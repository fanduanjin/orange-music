 ##drop DATABASE orange_music;

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
	plat_id int UNSIGNED,
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
alter table song add index index_plat_id(play_id);
alter table song add COLUMN singer_id int UNSIGNED;
alter table song add index index_singer_id(singer_id);
alter table song add COLUMN singer_plat_id int UNSIGNED;
alter table song add index index_singer_plat_id(singer_plat_id);
alter table singer add index index_plat_id(plat_id);

alter table song drop COLUMN singerId

create table property(
 id int UNSIGNED auto_increment PRIMARY key ,
 entity_type nvarchar(255),
 entity_id int UNSIGNED,
 `key` nvarchar(255),
 `value` Text
);

insert property values (DEFAULT,'cn.fan.User',1,'name','测试');
 SHOW TABLES

select * from singer LIMIT 1

select * from resource where resource_id=1439565987530805248 LIMIT 1

select count(1) from singer
select count(1) from song

select id,plat_Id,mid,name,type,area,genre,foreign_name,birthday,pic,
pic_resource_id,`desc`,wiki from singer where plat_id=4607 limit 1
select * from song where count(plat_id)>1

select * from song where media_resource_id=1439593837294129153
select * from singer where pic_resource_id=1439593837294129153 

delete from song where plat_id in(select plat_Id from (select plat_id from song GROUP BY plat_id  HAVING count(plat_id)>1) t1)

delete from singer where plat_id ='5512'
delete from resource
 
 
select * from song limit 100
 
select * from property limit 100

 