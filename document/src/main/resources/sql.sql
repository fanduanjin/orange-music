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

create table singerTeam(
	pid int UNSIGNED auto_increment primary key,
	teamId int UNSIGNED ,
	singerId int UNSIGNED,
	index(teamId)
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
2656
49
23778
17638

select MIN(pid) from singer
select * from information_schema.innodb_trx
delete from singer where pid <85000
delete from singerTeam where pid <85000

select * from singerTeam where teamId=1446099
select * from singer where id = 1446099

start slave
show slave status 
stop slave


CHANGE MASTER TO

MASTER_LOG_FILE='binlog.000011',
MASTER_LOG_POS=364;

select singer.pid, singer.id, singer.`name`, singer.mid, singer.type
	, singer.`desc`, singer.foreignName, singer.birthday, singer.wiki, singer.pic,tsinger.teamId 
from singer
	right join singerTeam tsinger on singer.id = tsinger.singerId
where tsinger.teamId = 1446099