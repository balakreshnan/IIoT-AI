drop table opcuadata

create table opcuadata
(
    deviceid varchar(400),
    tagName varchar(1000),
    tagdata varchar(1000),
    tagTime datetime,
    tagvalue decimal(18,9)
)

select top 100 * from opcuadata