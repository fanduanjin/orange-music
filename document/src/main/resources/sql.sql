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

create table song(
	id int UNSIGNED auto_increment primary key ,
	type int UNSIGNED,
	mid nvarchar(255),
	plat_Id int UNSIGNED,
	name nvarchar(255),
	title nvarchar(255),
	sub_title nvarchar(255),
	album_plat_id int UNSIGNED,
	mvid nvarchar(255),
	`language` int UNSIGNED,
	genre int UNSIGNED,
	public_time DATE,
	price_play boolean,
	media_mid nvarchar(255)
);
alter table song add COLUMN lrc Text;
alter table song add media_resource_id BIGINT UNSIGNED;

create table property(
 id int UNSIGNED auto_increment PRIMARY key ,
 entity_type nvarchar(255),
 entity_id int UNSIGNED,
 `key` nvarchar(255),
 `value` Text
);
 SHOW TABLES
 
select * from singer WHERE plat_id=19851

select count(1) from singer
select count(1) from song


select * from song;
 
 