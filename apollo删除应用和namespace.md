1.删除应用

（1）删除ApolloPortalDB中的数据（apollo port表）

#下面为逻辑删除：

#复制下面的sql内容，把第一行的appId从testApp改为实际要删除的appId，在ApolloPortalDB中执行即可。

set @appId = 'testApp';

Use ApolloPortalDB;

update App set IsDeleted = 1 where AppId = @appId and IsDeleted = 0;

update AppNamespace set IsDeleted = 1 where AppId = @appId and IsDeleted = 0;

update Favorite set IsDeleted = 1 where AppId = @appId and IsDeleted = 0;


create temporary table PermissionIds as select Id from Permission where (TargetId = @appId or TargetId like CONCAT(@appId, '+%'))  and IsDeleted = 0;

update Permission set IsDeleted = 1 where Id in (select Id from PermissionIds);

update RolePermission set IsDeleted = 1 where PermissionId in (select Id from PermissionIds);

drop temporary table PermissionIds;

create temporary table RoleIds as select Id from Role where (RoleName = CONCAT('Master+', @appId) or RoleName like CONCAT('ModifyNamespace+', @appId, '+%') or RoleName like CONCAT('ReleaseNamespace+', @appId, '+%')) and IsDeleted = 0;

update Role set IsDeleted = 1 where Id in (select Id from RoleIds);

update UserRole set IsDeleted = 1 where RoleId in (select Id from RoleIds);

update ConsumerRole set IsDeleted = 1 where RoleId in (select Id from RoleIds);

drop temporary table RoleIds;

#下面为物理删除：

set @appId = 'testApp';

Use ApolloPortalDB;

delete from table app a where a.AppId = @appId;

delete from table AppNamespace b where b.AppId = @appId;

delete from table Favorite f where f.AppId = @appId;//收藏应用信息表

create temporary table PermissionIds as select Id from Permission where (TargetId = @appId or TargetId like CONCAT(@appId, '+%'))  and IsDeleted = 0;

delete from RolePermission where PermissionId in (select Id from PermissionIds);

delete from Permission where Id in (select Id from PermissionIds);

drop temporary table PermissionIds;

create temporary table RoleIds as select Id from Role where (RoleName = CONCAT('Master+', @appId) or RoleName like CONCAT('ModifyNamespace+', @appId, '+%') or RoleName like CONCAT('ReleaseNamespace+', @appId, '+%')) and IsDeleted = 0;

delete from Role where Id in (select Id from RoleIds);

delete from UserRole where RoleId in (select Id from RoleIds);

delete from ConsumerRole where RoleId in (select Id from RoleIds);

drop temporary table RoleIds;


（2）删除ApolloConfigDB中的数据（apollo config 表）

#下面为逻辑删除：

#复制下面的sql内容，把第一行的appId从testApp改为实际要删除的appId，在每个环境的ApolloConfigDB中执行即可。

set @appId = 'testApp';

Use ApolloConfigDB;

update App set IsDeleted = 1 where AppId = @appId and IsDeleted = 0;

update AppNamespace set IsDeleted = 1 where AppId = @appId and IsDeleted = 0;

update Cluster set IsDeleted = 1 where AppId = @appId and IsDeleted = 0;

update Commit set IsDeleted = 1 where AppId = @appId and IsDeleted = 0;

update GrayReleaseRule set IsDeleted = 1 where AppId = @appId and IsDeleted = 0;

update Release set IsDeleted = 1 where AppId = @appId and IsDeleted = 0;

update ReleaseHistory set IsDeleted = 1 where AppId = @appId and IsDeleted = 0;

//以下三条为物理删除
delete from Instance where AppId = @appId;
delete from InstanceConfig where ConfigAppId = @appId;
delete from ReleaseMessage where Message like CONCAT(@appId, '+%');

create temporary table NamespaceIds as select Id from Namespace where AppId = @appId and IsDeleted = 0;
update Namespace set IsDeleted = 1 where Id in (select Id from NamespaceIds);
update Item set IsDeleted = 1 where NamespaceId in (select Id from NamespaceIds);
delete from NamespaceLock where NamespaceId in (select Id from NamespaceIds);
drop temporary table NamespaceIds;

#下面为物理删除：

delete from app where AppId = @appId;
delete from AppNamespace where AppId= @appId;
delete from Cluster where appId= @appId;
delete from Commit where appId = @appId;
delete from GrayReleaseRule where appId = @appId;
delete from Release where appId= @appId;
delete from ReleaseHistory where appId = @appId;
delete from Instance where AppId = @appId;
delete from InstanceConfig where ConfigAppId = @appId;
delete from ReleaseMessage where Message like CONCAT(@appId, '+%');

delete from Item where NamespaceId in(select Id from Namespace where AppId = @appId);
delete from Namespace where appId = @appId;
delete from NamespaceLock where NamespaceId in (select Id from NamespaceIds);

2.删除私有namespace（共有namespace可以在页面上删除）

#(1)删除ApolloPortalDB中的数据

#复制下面的sql内容，把第一和第二行的testApp和testNamespace分别改为实际要删除的appId和namespace，在ApolloPortalDB中执行即可。

set @appId = 'testApp';
set @namespaceName = 'testNamespace';

Use ApolloPortalDB`;

#下面为逻辑删除：

update AppNamespace set IsDeleted = 1 where AppId = @appId and Name = @namespaceName and IsPublic = 0 and IsDeleted = 0;

create temporary table PermissionIds as select Id from Permission where TargetId = CONCAT(@appId, '+', @namespaceName)  and IsDeleted = 0;
update Permission set IsDeleted = 1 where Id in (select Id from PermissionIds);
update RolePermission set IsDeleted = 1 where PermissionId in (select Id from PermissionIds);
drop temporary table PermissionIds;

create temporary table RoleIds as select Id from Role where (RoleName = CONCAT('ModifyNamespace+', @appId, '+', @namespaceName) or RoleName = CONCAT('ReleaseNamespace+', @appId, '+', @namespaceName)) and IsDeleted = 0;
update Role set IsDeleted = 1 where Id in (select Id from RoleIds);
update UserRole set IsDeleted = 1 where RoleId in (select Id from RoleIds`);
update ConsumerRole set IsDeleted = 1 where RoleId in (select Id from RoleIds`);
drop temporary table RoleIds;

#下面为物理删除：

delete from AppNamespace where AppId = @appId and Name = @namespaceName;

create temporary table PermissionIds as select Id from Permission where TargetId = CONCAT(@appId, '+', @namespaceName)  and IsDeleted = 0;
delete from permission where TargetId = CONCAT(@appId, '+', @namespaceName);
delete from RolePermission where PermissionId in (select Id from PermissionIds);
drop temporary table PermissionIds;

create temporary table RoleIds as select Id from Role where (RoleName = CONCAT('ModifyNamespace+', @appId, '+', @namespaceName) or RoleName = CONCAT('ReleaseNamespace+', @appId, '+', @namespaceName)) and IsDeleted = 0;
delete from Role where Id in (select Id from RoleIds);
delete from UserRole  where (select Id from RoleIds);
delete from ConsumerRole where RoleId in (select Id from RoleIds`);
drop temporary table RoleIds;

#（2）删除ApolloConfigDB中的数据

#复制下面的sql内容，把第一和第二行的testApp和testNamespace分别改为实际要删除的appId和namespace，在每个环境的ApolloConfigDB中执行即可。

#下面为逻辑删除：
set @appId = 'testApp';
set @namespaceName = 'testNamespace';

Use ApolloConfigDB;

update AppNamespace set IsDeleted = 1 where AppId = @appId and Name = @namespaceName and IsPublic = 0 and IsDeleted = 0;
update Commit set IsDeleted = 1 where AppId = @appId and NamespaceName = @namespaceName and IsDeleted = 0;
update GrayReleaseRule set IsDeleted = 1 where AppId = @appId and NamespaceName = @namespaceName and IsDeleted = 0;
update Release set IsDeleted = 1 where AppId = @appId and NamespaceName = @namespaceName and IsDeleted = 0;
update ReleaseHistory set IsDeleted = 1 where AppId = @appId and NamespaceName = @namespaceName and IsDeleted = 0;
delete from InstanceConfig where ConfigAppId = @appId and ConfigNamespaceName = @namespaceName;

create temporary table Namespaces as select Id, CONCAT_WS('+', AppId, ClusterName, NamespaceName) as ReleaseMessageKey from Namespace where AppId = @appId and NamespaceName = @namespaceName and IsDeleted = 0;
update Namespace set IsDeleted = 1 where Id in (select Id from Namespaces);
update Item set IsDeleted = 1 where NamespaceId in (select Id from Namespaces);
delete from NamespaceLock where NamespaceId in (select Id from Namespaces);
delete from ReleaseMessage where Message in (select ReleaseMessageKey from Namespaces);
drop temporary table Namespaces;

#下面为物理删除：

set @appId = 'testApp';
set @namespaceName = 'testNamespace';

Use ApolloConfigDB;

delete from AppNamespace where AppId = @appId and Name = @namespaceName;
delete from Commit where AppId = @appId and NamespaceName = @namespaceName;
delete from GrayReleaseRule where AppId = @appId and NamespaceName = @namespaceName;
delete from Release where AppId = @appId and NamespaceName = @namespaceName;
delete from ReleaseHistory where AppId = @appId and NamespaceName = @namespaceName;
delete from InstanceConfig where ConfigAppId = @appId and ConfigNamespaceName = @namespaceName;

create temporary table Namespaces as select Id, CONCAT_WS('+', AppId, ClusterName, NamespaceName) as ReleaseMessageKey from Namespace where AppId = @appId and NamespaceName = @namespaceName and IsDeleted = 0;
delete from Namespace where Id in (select Id from Namespaces);
delete from Item where NamespaceId in (select Id from Namespaces);
delete from NamespaceLock where NamespaceId in (select Id from Namespaces);
delete from ReleaseMessage where Message in (select ReleaseMessageKey from Namespaces);
drop temporary table Namespaces;
