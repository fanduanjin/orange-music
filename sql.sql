create database orange_music;
use orange_music;

create table singer (
	id int UNSIGNED auto_increment PRIMARY key ,
	plat_id int UNSIGNED COMMENT'平台主键',
	mid nvarchar(255),
	name nvarchar(255),
	type int UNSIGNED,
	area int UNSIGNED,
	`desc` TEXT	,
	wiki Text,
	genre int ,
	foreign_name nvarchar(255),
	birthday DATE,
  pic nvarchar(255),
	pic_resource_id BIGINT UNSIGNED COMMENT'resource.resource_id',
	INDEX(plat_id)
);


create table resource(
	id int UNSIGNED auto_increment PRIMARY key ,
	resource_id BIGINT  UNSIGNED,
  source_path nvarchar(255),
	dfs_path nvarchar(255),
	INDEX(resource_id)
);




delete from singer;
delete from resource;
show index from singer
drop table singer;
drop table resource;

select *from singer;
select *from resource;
SHOW FULL COLUMNS  from singer;

select singer.*,resource.dfs_path from singer left join resource
on singer.pic_resource_id=resource.resource_id