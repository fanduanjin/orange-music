create database orange_music;
use orange_music;

create table singer(
	id int UNSIGNED primary key,
	singerName nvarchar(255),
	singerMid nvarchar(25),
	singerPic nvarchar(255),
	singerDesc text
);


drop table singer;