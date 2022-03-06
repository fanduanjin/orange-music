 ##drop DATABASE orange_music;

create database orange_music;
use orange_music;


create table resource(
	pid int UNSIGNED auto_increment PRIMARY key ,
	resource_id BIGINT  UNSIGNED,
  source_path nvarchar(255),
	dfs_path nvarchar(255),
	INDEX(resource_id)
);

create table property(
	pid int UNSIGNED auto_increment primary key,
	entityType nvarchar(255),
	entityTypeCrc int UNSIGNED,
	entityId int UNSIGNED,
	`key` nvarchar(32),
	`value` nvarchar(255),
	index(entityTypeCrc)
);

alter table property add index index_entityId(entityId)

create table singer(
	pid int UNSIGNED auto_increment PRIMARY key ,
	id int UNSIGNED ,
	`name` nvarchar(255),
	mid nvarchar(255),
	type int UNSIGNED,
	`desc` char(32),
	foreignName nvarchar(255),
	birthday date,
	wiki char(32),
	pic nvarchar(255),
	INDEX(id),
	INDEX(mid),
	index(`name`)
);


create table song(
	pid int UNSIGNED auto_increment primary key ,
	id int UNSIGNED ,
	extraName nvarchar(255),
	mid nvarchar(255),
	title nvarchar(255),
	subTitle nvarchar(255),
	albumId int UNSIGNED,
	albumMid nvarchar(255),
	mvId int UNSIGNED,
	mvMid nvarchar(255),
	publicTime date ,
	lrc char(32),
	mediaUrl nvarchar(255),
	index(id),
	index(title)
)

show database
select * from property
delete from property where entityTypeCrc=331191905 and entityId=999999999 and entityType='cn.fan.model.music.Song'
