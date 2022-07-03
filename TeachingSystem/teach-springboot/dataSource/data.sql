-- =================教务管理系统================= --
create database teaching_system;

use teaching_system;

-- ----------------------------------------------
-- -----------------==日志表==----------------- --
-- ----------------------------------------------
create table `log`
(
    log_id      bigint primary key auto_increment comment '日志编号',
    deleted     int      not null default 0 comment '逻辑删除',
    create_time datetime not null default CURRENT_TIMESTAMP comment '添加时间',
    update_time datetime not null default CURRENT_TIMESTAMP comment '更新时间'
) comment '日志表';
-- ----------------------------------------------
-- -----------------==班级表==----------------- --
-- ----------------------------------------------
create table `class`
(
    class_id   bigint primary key auto_increment comment '班级编号',
    class_name varchar(32) not null comment '班级名',
    log_id     bigint      not null comment '日志编号',
    constraint cl_lg_fk foreign key (log_id) references log (log_id)
) comment '班级表';
-- ----------------------------------------------
-- -----------------==科目表==----------------- --
-- ----------------------------------------------
create table `subject`
(
    subject_id   bigint primary key auto_increment comment '科目编号',
    subject_name varchar(32) not null comment '科目名',
    log_id       bigint      not null comment '日志编号',
    constraint su_lg_fk foreign key (log_id) references log (log_id)
) comment '科目表';
-- ----------------------------------------------
-- -----------------==用户表==----------------- --
-- ----------------------------------------------
create table `user`
(
    user_id       bigint primary key auto_increment comment '用户编号',
    user_name     varchar(32) unique not null comment '账号',
    user_password varchar(64)        not null comment '密码',
    user_salt     varchar(64)        not null comment '盐值',
    user_header   varchar(64)        not null default 0 comment '用户头像',
    user_index    bigint             not null default 0 comment '身份编号',
    user_identity int                not null default 0 comment '身份码(0学生,1教师,2管理员)',
    log_id        bigint             not null comment '日志编号',
    constraint us_lg_fk foreign key (log_id) references log (log_id)
) comment '用户表';
-- ----------------------------------------------
-- -----------------==教师表==----------------- --
-- ----------------------------------------------
create table `teacher`
(
    teacher_id      bigint primary key auto_increment comment '教师编号',
    teacher_name    varchar(32) not null comment '教师姓名',
    teacher_age     int         not null comment '教师年龄',
    teacher_gender  int         not null comment '教师性别(0代表女,1代表男)',
    teacher_address varchar(64) not null comment '教师住址',
    teacher_phone   varchar(16) not null comment '教师电话',
    teacher_email   varchar(32) not null comment '教师邮箱',
    subject_id      bigint      not null comment '科目编号',
    log_id          bigint      not null comment '日志编号',
    constraint te_lg_fk foreign key (log_id) references log (log_id),
    constraint te_su_fk foreign key (subject_id) references subject (subject_id)
) comment '教师表';
-- ----------------------------------------------
-- -----------------==学生表==----------------- --
-- ----------------------------------------------
create table `student`
(
    student_id      bigint primary key auto_increment comment '学生编号',
    student_name    varchar(32) not null comment '学生姓名',
    student_age     int         not null comment '学生年龄',
    student_gender  int         not null comment '学生性别(0代表女,1代表男)',
    student_address varchar(64) not null comment '学生住址',
    student_phone   varchar(16) not null comment '学生电话',
    student_email   varchar(32) not null comment '学生邮箱',
    class_id        bigint      not null comment '班级编号',
    log_id          bigint      not null comment '日志编号',
    constraint st_lg_fk foreign key (log_id) references log (log_id),
    constraint st_cl_fk foreign key (class_id) references class (class_id)
) comment '学生表';

-- ----------------------------------------------
-- -----------------==管理员表==--------------  --
-- ----------------------------------------------
create table `admin`
(
    admin_id      bigint primary key auto_increment comment '管理员编号',
    admin_name    varchar(32) not null comment '管理员姓名',
    admin_age     int         not null comment '管理员年龄',
    admin_gender  int         not null comment '管理员性别(0代表女,1代表男)',
    admin_address varchar(64) not null comment '管理员住址',
    admin_phone   varchar(16) not null comment '管理员电话',
    admin_email   varchar(32) not null comment '管理员邮箱',
    log_id        bigint      not null comment '日志编号',
    constraint ad_lg_fk foreign key (log_id) references log (log_id)
) comment '管理员表';
-- ----------------------------------------------
-- -----------------==作业表==----------------- --
-- ----------------------------------------------
create table `create_work`
(
    work_id      bigint primary key auto_increment comment '作业编号',
    work_name    varchar(32) not null comment '作业名',
    work_content varchar(64) not null comment '作业内容(可传输文件)',
    teacher_id   bigint      not null comment '布置老师',
    class_id     bigint      not null comment '班级编号',
    log_id       bigint      not null comment '日志编号',
    constraint wk_lg_fk foreign key (log_id) references log (log_id),
    constraint wk_cl_fk foreign key (class_id) references class (class_id),
    constraint wk_te_fk foreign key (teacher_id) references teacher (teacher_id)
) comment '教师作业关系表';
-- ----------------------------------------------
-- -----------------==提交表==----------------- --
-- ----------------------------------------------
create table `submit_work`
(
    submit_id      bigint primary key auto_increment comment '提交编号',
    student_id     bigint      not null comment '提交学生',
    work_id        bigint      not null comment '作业编号',
    submit_content varchar(64) not null comment '提交内容(可传输文件)',
    submit_score   int         not null default 0 comment '提交分数',
    log_id         bigint      not null comment '日志编号',
    constraint suk_lg_fk foreign key (log_id) references log (log_id),
    constraint suk_crk_fk foreign key (work_id) references create_work (work_id),
    constraint suk_st_fk foreign key (student_id) references student (student_id)
) comment '学生作业关系表';

-- ----------------------------------------------
-- -----------------==测试数据==--------------- --
-- ----------------------------------------------
INSERT INTO `log` VALUES (1, 0, '2022-04-06 13:59:17', '2022-04-06 13:59:17');
INSERT INTO `log` VALUES (2, 0, '2022-04-06 13:59:20', '2022-04-06 13:59:20');
INSERT INTO `log` VALUES (3, 0, '2022-04-06 13:59:21', '2022-04-06 13:59:21');
INSERT INTO `log` VALUES (4, 0, '2022-04-06 13:59:22', '2022-04-06 13:59:22');
INSERT INTO `log` VALUES (5, 0, '2022-04-06 14:01:32', '2022-04-06 14:01:32');
INSERT INTO `log` VALUES (6, 0, '2022-04-06 14:01:33', '2022-04-06 14:01:33');
INSERT INTO `log` VALUES (7, 0, '2022-04-06 14:01:34', '2022-04-06 14:01:34');
INSERT INTO `log` VALUES (8, 0, '2022-04-06 14:01:35', '2022-04-06 14:01:35');
INSERT INTO `log` VALUES (9, 0, '2022-04-06 02:02:49', '2022-06-23 19:16:18');
INSERT INTO `log` VALUES (10, 0, '2022-04-06 02:05:46', '2022-06-23 19:22:15');
INSERT INTO `log` VALUES (12, 0, '2022-04-07 09:00:14', '2022-04-22 18:17:41');
INSERT INTO `log` VALUES (13, 0, '2022-04-07 10:21:48', '2022-04-07 10:22:56');
INSERT INTO `log` VALUES (15, 0, '2022-04-07 10:27:35', '2022-06-23 11:34:56');
INSERT INTO `log` VALUES (16, 0, '2022-04-07 02:31:08', '2022-04-07 02:33:02');
INSERT INTO `log` VALUES (17, 0, '2022-04-07 02:59:40', '2022-04-07 03:01:46');
INSERT INTO `log` VALUES (22, 0, '2022-04-13 08:57:31', '2022-04-13 08:57:31');
INSERT INTO `log` VALUES (23, 0, '2022-04-13 08:57:52', '2022-04-22 16:59:38');
INSERT INTO `log` VALUES (24, 0, '2022-04-13 08:58:37', '2022-04-13 08:58:37');
INSERT INTO `log` VALUES (29, 0, '2022-04-14 09:46:54', '2022-04-14 10:28:08');
INSERT INTO `log` VALUES (30, 0, '2022-04-14 10:23:44', '2022-04-15 15:47:00');
INSERT INTO `log` VALUES (32, 0, '2022-04-14 04:28:50', '2022-04-14 16:28:50');
INSERT INTO `log` VALUES (33, 0, '2022-04-14 06:48:42', '2022-04-14 18:48:42');
INSERT INTO `log` VALUES (34, 0, '2022-04-16 16:14:39', '2022-04-16 16:14:39');
INSERT INTO `log` VALUES (37, 0, '2022-04-17 02:34:32', '2022-04-17 14:34:32');
INSERT INTO `log` VALUES (42, 0, '2022-04-18 06:36:01', '2022-04-18 18:36:01');
INSERT INTO `log` VALUES (43, 0, '2022-04-18 06:37:38', '2022-04-18 18:37:38');
INSERT INTO `log` VALUES (44, 0, '2022-04-18 06:39:22', '2022-04-18 18:39:22');
INSERT INTO `log` VALUES (46, 0, '2022-04-18 06:39:58', '2022-04-18 18:39:58');
INSERT INTO `log` VALUES (47, 0, '2022-04-18 06:41:49', '2022-04-18 18:41:49');
INSERT INTO `log` VALUES (48, 0, '2022-04-19 15:22:43', '2022-06-09 18:07:25');
INSERT INTO `log` VALUES (49, 0, '2022-04-19 15:23:34', '2022-05-03 08:24:33');
INSERT INTO `log` VALUES (50, 0, '2022-04-19 15:24:34', '2022-04-20 09:11:18');
INSERT INTO `log` VALUES (52, 0, '2022-04-20 09:54:08', '2022-04-20 09:54:08');
INSERT INTO `log` VALUES (53, 0, '2022-04-22 10:45:27', '2022-05-13 22:49:36');
INSERT INTO `log` VALUES (54, 0, '2022-04-22 10:54:04', '2022-04-22 10:54:04');
INSERT INTO `log` VALUES (55, 0, '2022-04-22 01:59:17', '2022-06-23 17:20:13');
INSERT INTO `log` VALUES (56, 0, '2022-04-22 03:47:58', '2022-05-19 08:39:57');
INSERT INTO `log` VALUES (57, 0, '2022-04-22 16:41:45', '2022-04-22 16:51:29');
INSERT INTO `log` VALUES (58, 0, '2022-04-22 16:53:16', '2022-04-22 16:53:16');
INSERT INTO `log` VALUES (59, 0, '2022-04-22 18:16:49', '2022-04-22 19:02:03');
INSERT INTO `log` VALUES (60, 0, '2022-04-22 18:39:11', '2022-05-02 17:28:23');
INSERT INTO `log` VALUES (61, 0, '2022-04-22 18:39:57', '2022-05-02 17:34:12');
INSERT INTO `log` VALUES (62, 0, '2022-04-22 18:52:59', '2022-04-22 18:53:16');
INSERT INTO `log` VALUES (63, 0, '2022-04-22 18:53:35', '2022-04-22 18:53:35');
INSERT INTO `log` VALUES (64, 0, '2022-04-22 18:54:42', '2022-04-22 19:38:29');
INSERT INTO `log` VALUES (65, 0, '2022-04-22 18:59:46', '2022-04-22 19:00:05');
INSERT INTO `log` VALUES (66, 0, '2022-04-22 19:03:19', '2022-04-22 19:03:19');
INSERT INTO `log` VALUES (68, 0, '2022-04-22 07:04:40', '2022-04-22 19:04:40');
INSERT INTO `log` VALUES (69, 0, '2022-04-22 19:12:57', '2022-04-22 19:12:57');
INSERT INTO `log` VALUES (70, 0, '2022-04-22 19:13:18', '2022-05-02 17:34:54');
INSERT INTO `log` VALUES (71, 0, '2022-04-22 19:15:37', '2022-04-22 19:15:37');
INSERT INTO `log` VALUES (72, 0, '2022-04-22 19:16:08', '2022-05-02 18:41:34');
INSERT INTO `log` VALUES (73, 0, '2022-04-22 19:16:54', '2022-04-22 19:16:54');

INSERT INTO `user` VALUES (1, 'root', '36B5049766BDA814D7A9D4F382673827', '1D9CD5B6-00A7-4415-AD48-E3ADA50E95C4', '1539930344914968577', 1, 0, 9);
INSERT INTO `user` VALUES (2, 'root1', '57F9E73F72DDE394E620D45E2370175C', '6A91ECA1-DEB7-44DD-AF61-E509540F86FF', '1539931840746381313', 1, 1, 10);
INSERT INTO `user` VALUES (3, 'root3', 'A8DCB9396F1F87ED45F5DBC05930AC7D', '422A5781-21EA-4963-ADE7-364939EFBF17', 'E7241324-5D90-46F4-A692-EF92369C54BA.png', 2, 0, 32);
INSERT INTO `user` VALUES (4, 'root4', '7A423DA158D360B7CE8DFF1A79B4884C', 'F8AEAD9A-1D8B-4D7F-9914-CB85D0BEED24', '0', 0, 0, 33);
INSERT INTO `user` VALUES (5, 'root2', 'E435AACB686ED0C97575BFFD71403377', '08A076E8-5F9E-4D22-BE9F-78DE354E0C4D', '0', 0, 0, 37);
INSERT INTO `user` VALUES (9, 'root5', 'CF74C08FB2E251B51CA9994CEBB6CA2F', 'B48533DB-A70C-4B8C-B0E2-D8EF1A24FAB5', '888A559E-D431-4A27-876C-AD95CAA8C602.png', 3, 1, 46);
INSERT INTO `user` VALUES (10, 'root6', '0DD1DEF2DCE31C8565607B47973E3437', '94B4C261-88B2-424C-A86A-C360729E846D', '0', 0, 1, 47);
INSERT INTO `user` VALUES (11, 'teacher1', 'E8BBE906DBF5C44A6F11F197E56701BF', 'A44A222D-4812-40E3-9221-9C4765B4F7B5', '0', 0, 1, 54);
INSERT INTO `user` VALUES (12, 'admin1', 'CB739874244ABDB6D6B628CCD6C42AE6', 'EEB6C4B1-EDAD-4D9C-823C-BCB3A178297B', '1539901130383867905', 1, 2, 55);
INSERT INTO `user` VALUES (13, 'admin2', '41A550F683E58CCFDB0C01FAE2291FF3', '6E781D85-DDF4-4FE4-B7E5-8D8763BD2D6A', '1527086624637812737', 2, 2, 56);
INSERT INTO `user` VALUES (14, 'admin3', '79437FF9F510DC00C2F3146BAD26C229', 'FA2D11DC-D7B1-4332-AD92-0273D7AF6493', '0', 0, 2, 68);

INSERT INTO `class` VALUES (1, '计算机01', 1);
INSERT INTO `class` VALUES (2, '计算机02', 2);
INSERT INTO `class` VALUES (3, '计算机03', 16);
INSERT INTO `class` VALUES (4, '计算机04', 62);
INSERT INTO `class` VALUES (5, '电商01', 63);
INSERT INTO `class` VALUES (6, '电商02', 64);
INSERT INTO `class` VALUES (7, '电商03', 72);

INSERT INTO `admin` VALUES (1, 'root', 19, 0, '北京', '15692468756', '1564896325@qq.com', 53);
INSERT INTO `admin` VALUES (2, 'root2', 19, 0, '上海', '15698456978', '8546932568@qq.com', 60);
INSERT INTO `admin` VALUES (3, 'root3', 18, 1, '上海', '15698743652', '1456987456@qq.com', 61);
INSERT INTO `admin` VALUES (4, 'root4', 18, 0, '武汉', '15698485964', '1258745896@qq.com', 73);

INSERT INTO `student` VALUES (1, '小明', 19, 1, '北京', '15693258746', '2567534987@qq.com', 1, 3);
INSERT INTO `student` VALUES (2, '小红', 18, 0, '上海', '15698743256', '2567534987@qq.com', 2, 4);
INSERT INTO `student` VALUES (3, '小花', 19, 0, '上海', '15632894578', '2987056231@qq.com', 1, 22);
INSERT INTO `student` VALUES (4, 'frisk', 19, 0, 'England', '15632894578', '2987056231@qq.com', 2, 23);
INSERT INTO `student` VALUES (5, '李华', 20, 1, '北京', '15689453256', '4587914568@qq.com', 1, 24);
INSERT INTO `student` VALUES (6, 'student1', 19, 1, '北京', '15698743652', '1568748596@qq.com', 3, 57);
INSERT INTO `student` VALUES (7, 'student2', 18, 1, '上海', '15687469584', '2398734123@qq.com', 3, 58);
INSERT INTO `student` VALUES (8, 'student3', 19, 0, '北京', '15698746325', '4569826358@qq.com', 5, 66);
INSERT INTO `student` VALUES (9, 'student4', 18, 1, '北京', '15698746582', '4587965236@qq.com', 3, 71);

INSERT INTO `subject` VALUES (1, '计算机基础', 7);
INSERT INTO `subject` VALUES (2, 'C语言程序设计基础', 17);
INSERT INTO `subject` VALUES (3, 'java语言设计基础', 65);
INSERT INTO `subject` VALUES (4, 'python语言设计基础', 69);
INSERT INTO `subject` VALUES (5, '静态网页设计基础', 70);

INSERT INTO `teacher` VALUES (1, '李老师', 36, 1, '北京', '15692846325', '2876509234@qq.com', 1, 8);
INSERT INTO `teacher` VALUES (3, '王老师', 23, 0, '北京', '15693258469', '2563148965@qq.com', 1, 12);
INSERT INTO `teacher` VALUES (4, '黄老师', 19, 1, '上海', '15698746325', '2487613098@qq.com', 2, 59);

INSERT INTO `create_work` VALUES (1, 'java', '111', 1, 1, 13);
INSERT INTO `create_work` VALUES (2, 'python', '在控制台打印三角形', 1, 1, 48);
INSERT INTO `create_work` VALUES (3, 'C语言', '完成第一个c语言程序', 1, 2, 49);
INSERT INTO `create_work` VALUES (4, '高等数学', '完成数学练习', 1, 1, 50);
INSERT INTO `create_work` VALUES (5, 'java', '111111', 3, 1, 52);

INSERT INTO `submit_work` VALUES (1, 1, 1, '2222', 85, 15);
INSERT INTO `submit_work` VALUES (6, 1, 1, '3333', 70, 29);
INSERT INTO `submit_work` VALUES (7, 1, 1, '55554', 85, 30);
INSERT INTO `submit_work` VALUES (8, 2, 1, '4444', 98, 34);