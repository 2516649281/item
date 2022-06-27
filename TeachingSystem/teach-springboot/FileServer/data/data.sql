create database FileServer;

use FileServer;

-- 文件表
create table file
(
    file_id          bigint primary key auto_increment comment '文件编号',
    file_name        varchar(64)  not null comment '文件名',
    file_path        varchar(256) not null comment '文件路径',
    file_type        varchar(16)  not null comment '文件类型',
    file_size        varchar(16)  not null comment '文件大小',
    file_upload_time datetime     not null default CURRENT_TIMESTAMP comment '上传时间',
    file_update_time datetime     not null default CURRENT_TIMESTAMP comment '修改时间'
) comment '文件表';

-- 测试数据
INSERT INTO `file`
VALUES (1526859784438472705, '7D64960D-71AC-4FDA-BF22-F8CB2AE1151C.png',
        'D:/Test/7D64960D-71AC-4FDA-BF22-F8CB2AE1151C.png', 'png', '56502K', '2022-05-18 05:38:33',
        '2022-05-18 05:38:33');
INSERT INTO `file`
VALUES (1526868727550238721, '58A6F50A-0854-480B-8D11-6D47B0B6FEDD.png',
        'D:/Test/58A6F50A-0854-480B-8D11-6D47B0B6FEDD.png', 'png', '56502K', '2022-05-18 06:14:04',
        '2022-05-18 06:14:04');
INSERT INTO `file`
VALUES (1526876480549216258, '607F93DF-6029-47D0-B3D8-3D403C56AF83.png',
        'D:/Test/607F93DF-6029-47D0-B3D8-3D403C56AF83.png', 'png', '56502K', '2022-05-18 06:44:55',
        '2022-05-18 06:44:55');
INSERT INTO `file`
VALUES (1526876480561799169, 'CFA9F5CE-C128-4063-8CF6-15B1437F5438.png',
        'D:/Test/CFA9F5CE-C128-4063-8CF6-15B1437F5438.png', 'png', '56502K', '2022-05-18 06:44:55',
        '2022-05-18 06:44:55');
INSERT INTO `file`
VALUES (1526876859106123778, '2A5911AC-3F29-4692-8B2B-49E43E19E993.png',
        'D:/Test/2A5911AC-3F29-4692-8B2B-49E43E19E993.png', 'png', '56502K', '2022-05-18 06:46:25',
        '2022-05-18 06:46:25');
INSERT INTO `file`
VALUES (1526877736223170561, '0D7BDA5D-4F4E-4224-AD45-ADAD9180A482.png',
        'D:/Test/0D7BDA5D-4F4E-4224-AD45-ADAD9180A482.png', 'png', '56502K', '2022-05-18 06:49:54',
        '2022-05-18 06:49:54');
INSERT INTO `file`
VALUES (1526878349870862337, '49EA6814-AE45-47E6-91EB-7BAF93C0811A.png',
        'D:/Test/49EA6814-AE45-47E6-91EB-7BAF93C0811A.png', 'png', '56502K', '2022-05-18 06:52:21',
        '2022-05-18 06:52:21');
INSERT INTO `file`
VALUES (1527085735894155265, '29237E54-5963-4484-97FF-4BEF7574793B.png',
        'D:/Test/29237E54-5963-4484-97FF-4BEF7574793B.png', 'png', '56502K', '2022-05-19 08:36:25',
        '2022-05-19 08:36:25');
INSERT INTO `file`
VALUES (1527086214254526465, 'B2C9B582-DBAA-44EA-8085-3D464124F775.png',
        'D:/Test/B2C9B582-DBAA-44EA-8085-3D464124F775.png', 'png', '7648K', '2022-05-19 08:38:20',
        '2022-05-19 08:38:20');
INSERT INTO `file`
VALUES (1527086624637812737, '9C471DDF-E93C-4C18-85BD-A29E6E1CA9AB.png',
        'D:/Test/9C471DDF-E93C-4C18-85BD-A29E6E1CA9AB.png', 'png', '56502K', '2022-05-19 08:39:57',
        '2022-05-19 08:39:57');
INSERT INTO `file`
VALUES (1539901130383867905, '7316B6BC-AB28-4E25-AB0D-A22686D50579.png',
        'D:/Test/7316B6BC-AB28-4E25-AB0D-A22686D50579.png', 'png', '113215K', '2022-06-23 17:20:14',
        '2022-06-23 17:20:14');
INSERT INTO `file`
VALUES (1540956545045516289, 'F5DF3C3E-A731-47EA-B341-521A4C13261B.png',
        'D:/Test/F5DF3C3E-A731-47EA-B341-521A4C13261B.png', 'png', '86820K', '2022-06-26 15:14:04',
        '2022-06-26 15:14:04');
INSERT INTO `file`
VALUES (1540960588992106498, '7B139AB0-A400-4A89-9D59-8F3117EF2005.png',
        'D:/Test/7B139AB0-A400-4A89-9D59-8F3117EF2005.png', 'png', '113215K', '2022-06-26 15:30:08',
        '2022-06-26 15:30:08');