一.删除应用

1.删除ApolloPortalDB中的数据（apollo port表）

复制下面的sql内容，把第一行的appId从testApp改为实际要删除的appId，在ApolloPortalDB中执行即可。

（1）下面为逻辑删除：

set @appId = 'test_operater';

Use `apolloportaldb`;

update `App` set `IsDeleted` = 1 where `AppId` = @appId and `IsDeleted` = 0;
update `AppNamespace` set `IsDeleted` = 1 where `AppId` = @appId and `IsDeleted` = 0;
update `Favorite` set `IsDeleted` = 1 where `AppId` = @appId and `IsDeleted` = 0;

# handle roles and permissions
create temporary table `PermissionIds` as select `Id` from `Permission` where (`TargetId` = @appId or `TargetId` like CONCAT(@appId, '+%'))  and `IsDeleted` = 0;
update `Permission` set `IsDeleted` = 1 where `Id` in (select `Id` from `PermissionIds`);
update `RolePermission` set `IsDeleted` = 1 where `PermissionId` in (select `Id` from `PermissionIds`);
drop temporary table `PermissionIds`;

create temporary table `RoleIds` as select `Id` from `Role` where (`RoleName` = CONCAT('Master+', @appId) or `RoleName` like CONCAT('ModifyNamespace+', @appId, '+%') or `RoleName` like CONCAT('ReleaseNamespace+', @appId, '+%')) and `IsDeleted` = 0;
update `Role` set `IsDeleted` = 1 where `Id` in (select `Id` from `RoleIds`);
update `UserRole` set `IsDeleted` = 1 where `RoleId` in (select `Id` from `RoleIds`);
update `ConsumerRole` set `IsDeleted` = 1 where `RoleId` in (select `Id` from `RoleIds`);
drop temporary table `RoleIds`;

（2）下面为物理删除：

set @appId = 'test_operater';

Use `apolloportaldb`;

delete from `App` where `AppId` = @appId;
delete from `AppNamespace` where `AppId` = @appId;
delete from `Favorite` where `AppId` = @appId;

# handle roles and permissions
create temporary table `PermissionIds` as select `Id` from `Permission` where (`TargetId` = @appId or `TargetId` like CONCAT(@appId, '+%'));
delete from `Permission` where `Id` in (select `Id` from `PermissionIds`);
delete from `RolePermission` where `PermissionId` in (select `Id` from `PermissionIds`);
drop temporary table `PermissionIds`;

create temporary table `RoleIds` as select `Id` from `Role` where (`RoleName` = CONCAT('Master+', @appId) or `RoleName` like CONCAT('ModifyNamespace+', @appId, '+%') or `RoleName` like CONCAT('ReleaseNamespace+', @appId, '+%'));
delete from `Role` where `Id` in (select `Id` from `RoleIds`);
delete from `UserRole` where `RoleId` in (select `Id` from `RoleIds`);
delete from `ConsumerRole` where `RoleId` in (select `Id` from `RoleIds`);
drop temporary table `RoleIds`;


2.删除ApolloConfigDB中的数据（apollo config 表）

复制下面的sql内容，把第一行的appId从testApp改为实际要删除的appId，在每个环境的ApolloConfigDB中执行即可。

（1）下面为逻辑删除：

set @appId = 'test_operater';

Use `apolloconfigdb_test`;

update `App` set `IsDeleted` = 1 where `AppId` = @appId and `IsDeleted` = 0;
update `AppNamespace` set `IsDeleted` = 1 where `AppId` = @appId and `IsDeleted` = 0;
update `Cluster` set `IsDeleted` = 1 where `AppId` = @appId and `IsDeleted` = 0;
update `Commit` set `IsDeleted` = 1 where `AppId` = @appId and `IsDeleted` = 0;
update `GrayReleaseRule` set `IsDeleted` = 1 where `AppId` = @appId and `IsDeleted` = 0;
update `Release` set `IsDeleted` = 1 where `AppId` = @appId and `IsDeleted` = 0;
update `ReleaseHistory` set `IsDeleted` = 1 where `AppId` = @appId and `IsDeleted` = 0;
delete from `Instance` where `AppId` = @appId;
delete from `InstanceConfig` where `ConfigAppId` = @appId;
delete from `ReleaseMessage` where `Message` like CONCAT(@appId, '+%');

# handle namespaces and items
create temporary table `NamespaceIds` as select `Id` from `Namespace` where `AppId` = @appId and `IsDeleted` = 0;
update `Namespace` set `IsDeleted` = 1 where `Id` in (select `Id` from `NamespaceIds`);
update `Item` set `IsDeleted` = 1 where `NamespaceId` in (select `Id` from `NamespaceIds`);
delete from `NamespaceLock` where `NamespaceId` in (select `Id` from `NamespaceIds`);
drop temporary table `NamespaceIds`;

（2）下面为物理删除：

set @appId = 'test_operater';

Use `apolloconfigdb_test`;

delete from `App` where `AppId` = @appId;
delete from `AppNamespace` where `AppId` = @appId;
delete from `Cluster` where `AppId` = @appId;
delete from `Commit` where `AppId` = @appId;
delete from `GrayReleaseRule` where `AppId` = @appId;
delete from `Release` where `AppId` = @appId;
delete from `ReleaseHistory` where `AppId` = @appId;
delete from `Instance` where `AppId` = @appId;
delete from `InstanceConfig` where `ConfigAppId` = @appId;
delete from `ReleaseMessage` where `Message` like CONCAT(@appId, '+%');

# handle namespaces and items
create temporary table `NamespaceIds` as select `Id` from `Namespace` where `AppId` = @appId;
delete from `Namespace` where `Id` in (select `Id` from `NamespaceIds`);
delete from `Item` where `NamespaceId` in (select `Id` from `NamespaceIds`);
delete from `NamespaceLock` where `NamespaceId` in (select `Id` from `NamespaceIds`);
drop temporary table `NamespaceIds`;

---------------------------------------------------------------------------------------------------------------------------------------------------

二.删除私有namespace（公共namespace可以在页面上删除）

1.删除ApolloPortalDB中的数据

复制下面的sql内容，把第一和第二行的testApp和testNamespace分别改为实际要删除的appId和namespace，在ApolloPortalDB中执行即可。

（1）下面为逻辑删除：

set @appId = 'test_operater';

set @namespaceName = 'test-wy';

Use `apolloportaldb`;

update `AppNamespace` set `IsDeleted` = 1 where `AppId` = @appId and `Name` = @namespaceName and `IsPublic` = 0 and `IsDeleted` = 0;

# handle roles and permissions
create temporary table `PermissionIds` as select `Id` from `Permission` where `TargetId` = CONCAT(@appId, '+', @namespaceName)  and `IsDeleted` = 0;
update `Permission` set `IsDeleted` = 1 where `Id` in (select `Id` from `PermissionIds`);
update `RolePermission` set `IsDeleted` = 1 where `PermissionId` in (select `Id` from `PermissionIds`);
drop temporary table `PermissionIds`;

create temporary table `RoleIds` as select `Id` from `Role` where (`RoleName` = CONCAT('ModifyNamespace+', @appId, '+', @namespaceName) or `RoleName` = CONCAT('ReleaseNamespace+', @appId, '+', @namespaceName)) and `IsDeleted` = 0;
update `Role` set `IsDeleted` = 1 where `Id` in (select `Id` from `RoleIds`);
update `UserRole` set `IsDeleted` = 1 where `RoleId` in (select `Id` from `RoleIds`);
update `ConsumerRole` set `IsDeleted` = 1 where `RoleId` in (select `Id` from `RoleIds`);
drop temporary table `RoleIds`;

（2）下面为物理删除：

set @appId = 'test_operater';

set @namespaceName = 'test-wy';

Use `apolloportaldb`;

delete from `AppNamespace`  where `AppId` =@ppId and `Name` = @namespaceName;

# handle roles and permissions
create temporary table `PermissionIds` as select `Id` from `Permission` where `TargetId` = CONCAT(@appId, '+', @namespaceName);
delete from `Permission` where `Id` in (select `Id` from `PermissionIds`);
delete from `RolePermission` where `PermissionId` in (select `Id` from `PermissionIds`);
drop temporary table `PermissionIds`;

create temporary table `RoleIds` as select `Id` from `Role` where (`RoleName` = CONCAT('ModifyNamespace+', @appId, '+', @namespaceName) or `RoleName` = CONCAT('ReleaseNamespace+', @appId, '+', @namespaceName));
delete from `Role` where `Id` in (select `Id` from `RoleIds`);
delete from `UserRole` where `RoleId` in (select `Id` from `RoleIds`);
delete from `ConsumerRole` where `RoleId` in (select `Id` from `RoleIds`);
drop temporary table `RoleIds`;

2.删除ApolloConfigDB中的数据

复制下面的sql内容，把第一和第二行的testApp和testNamespace分别改为实际要删除的appId和namespace，在每个环境的ApolloConfigDB中执行即可。

（1）下面为逻辑删除：

set @appId = 'test_operater';

set @namespaceName = 'test-wy';

Use apolloconfigdb_test;
update `AppNamespace` set `IsDeleted` = 1 where `AppId` = @appId and `Name` = @namespaceName and `IsPublic` = 0 and `IsDeleted` = 0;
update `Commit` set `IsDeleted` = 1 where `AppId` = @appId and `NamespaceName` = @namespaceName and `IsDeleted` = 0;
update `GrayReleaseRule` set `IsDeleted` = 1 where `AppId` = @appId and `NamespaceName` = @namespaceName and `IsDeleted` = 0;
update `Release` set `IsDeleted` = 1 where `AppId` = @appId and `NamespaceName` = @namespaceName and `IsDeleted` = 0;
update `ReleaseHistory` set `IsDeleted` = 1 where `AppId` = @appId and `NamespaceName` = @namespaceName and `IsDeleted` = 0;
delete from `InstanceConfig` where `ConfigAppId` = @appId and `ConfigNamespaceName` = @namespaceName;

# handle namespaces, items and release messages
create temporary table `Namespaces` as select `Id`, CONCAT_WS('+', `AppId`, `ClusterName`, `NamespaceName`) as `ReleaseMessageKey` from `Namespace` where `AppId` = @appId and `NamespaceName` = @namespaceName and `IsDeleted` = 0;
update `Namespace` set `IsDeleted` = 1 where `Id` in (select `Id` from `Namespaces`);
update `Item` set `IsDeleted` = 1 where `NamespaceId` in (select `Id` from `Namespaces`);
delete from `NamespaceLock` where `NamespaceId` in (select `Id` from `Namespaces`);
delete from `ReleaseMessage` where `Message` in (select `ReleaseMessageKey` from `Namespaces`);
drop temporary table `Namespaces`;

（2）下面为物理删除：

set @appId = 'test_operater';

set @namespaceName = 'test-wy';

Use apolloconfigdb_test;

delete from `AppNamespace` where `AppId` = @appId and `Name` = @namespaceName;
delete from `Commit` where `AppId` = @appId and `NamespaceName` = @namespaceName;
delete from `GrayReleaseRule` where `AppId` = @appId and `NamespaceName` = @namespaceName;
delete from `Release` where `AppId` = @appId and `NamespaceName` = @namespaceName;
delete from `ReleaseHistory` where `AppId` = @appId and `NamespaceName` = @namespaceName;
delete from `InstanceConfig` where `ConfigAppId` = @appId and `ConfigNamespaceName` = @namespaceName;

# handle namespaces, items and release messages
create temporary table `Namespaces` as select `Id`, CONCAT_WS('+', `AppId`, `ClusterName`, `NamespaceName`) as `ReleaseMessageKey` from `Namespace` where `AppId` = @appId and `NamespaceName` = @namespaceName;
delete from `Namespace` where `Id` in (select `Id` from `Namespaces`);
delete from `Item` where `NamespaceId` in (select `Id` from `Namespaces`);
delete from `NamespaceLock` where `NamespaceId` in (select `Id` from `Namespaces`);
delete from `ReleaseMessage` where `Message` in (select `ReleaseMessageKey` from `Namespaces`);
drop temporary table `Namespaces`;
