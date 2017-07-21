/* create tables */

create table trainer
	(id int unsigned not null auto_increment,
    fname varchar(20) not null,
    lname varchar(20) not null,
    username varchar(20) not null unique,
    password varchar(20) not null,
    constraint pk_trainer primary key (id)
	);

create table plan
	(id int unsigned not null auto_increment,
    trainer_id int unsigned,
    calories smallint unsigned,
    protein smallint unsigned,
    carbs smallint unsigned,
    fat smallint unsigned,
    water smallint unsigned,
    constraint fk_p_trainer_id foreign key (trainer_id)
		references trainer (id),
	constraint pk_plan primary key (id)
    );

create table user 
	(id int unsigned not null auto_increment, 
    fname varchar(20) not null, 
    lname varchar(20) not null, 
    username varchar(20) not null unique,
    password varchar(20) not null,
    trainer_id int unsigned,
    plan_id int unsigned,
    constraint fk_u_trainer_id foreign key (trainer_id)
		references trainer (id),
	constraint fk_u_plan_id foreign key (plan_id)
		references plan (id),
    constraint pk_user primary key (id)
	);
    
create table water_diary
	(id int unsigned not null auto_increment,
    entry_date date,
    quantity smallint unsigned,
    user_id int unsigned,
    constraint pk_water_diary primary key (id)
    );
    
create table food_db
	(id int unsigned not null auto_increment,
    name varchar(30) not null,
    calories smallint unsigned not null,
    protein smallint unsigned not null,
    carbs smallint unsigned not null,
    fat smallint unsigned not null,
    portion_size varchar(15) not null,
    constraint pk_food_db primary key (id)
    );

create table diet_diary
	(id int unsigned not null auto_increment,
    entry_date date,
    food varchar(20),
    calories smallint unsigned,
    protein smallint unsigned,
    carbs smallint unsigned,
    fat smallint unsigned,
    quantity smallint unsigned,
    food_id int unsigned,
    user_id int unsigned,
    constraint fk_d_user_id foreign key (user_id)
		references user (id),
	constraint fk_d_food_id foreign key (food_id)
		references food_db (id),
	constraint pk_diet_diary primary key (id)
	);
    
/* insert foods into the food database */
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Chicken Breast', '1 oz', 35, 1, 0, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Turkey Breast', '1 oz', 29, 1, 0, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Oatmeal', '1/4 cup', 38, 0, 1, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Almonds', '3', 21, 0, 0, 1);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('1% Milk', '1 cup', 103, 1, 1, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Beef', '1 oz', 63, 1, 0, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Canadian Bacon', '1 oz', 52, 1, 0, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Pork', '1 oz', 54, 1, 0, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Lamb', '1 oz', 61, 1, 0, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Salmon', '1 oz', 41, 1, 0, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Soft Tofu', '3 oz', 60, 1, 0, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Whole Egg', '1 large', 74, 1, 0, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Egg Whites', '2 large', 34, 1, 0, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Cooked Artichoke', '1 small', 69, 0, 1, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Cooked Green Beans', '1 cup', 77, 0, 1, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Brocolli', '2 cups', 62, 0, 1, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Cucumber', '1 (8-1/4 in.)', 45, 0, 1, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Apple', '1/2 medium', 36, 0, 1, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Grapes', '1/2 cup', 55, 0, 1, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Kiwi', '1 fruit', 46, 0, 1, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Orange', '1/2 large', 43, 0, 1, 0);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Peanut Butter', '1/2 tbsp', 47, 0, 0, 1);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Guacamole', '1/2 tbsp', 12, 0, 0, 1);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Mayonnaise', '1/2 tbsp', 28, 0, 0, 1);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Sour Cream', '1 tbsp', 31, 0, 0, 1);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Tartar Sauce', '1/2 tbsp', 37, 0, 0, 1);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Peanuts', '6', 36, 0, 0, 1);
insert into food_db (name, portion_size, calories, protein, carbs, fat)
values ('Half and Half', '1 tbsp', 20, 0, 0, 1);

/* insert the default trainer and plan */
insert into trainer (id, fname, lname, username, password)
values (1, 'No', 'Trainer', 'no1', 'W@yk8utK&10aBz!');
insert into plan (id, trainer_id, calories, protein, carbs, fat, water)
values (1, 1, 2000, 13, 13, 13, 6);

/* insert trainers */
insert into trainer (id, fname, lname, username, password)
values (2, 'John', 'Doe', 'jdoe442', 'itrainu14');
insert into trainer (fname, lname, username, password)
values ('Fletch', 'Skinner', 'flinner21', '12345679');
insert into trainer (fname, lname, username, password)
values ('Malcolm', 'Function', 'trynnafunction', 'baseball');
insert into trainer (fname, lname, username, password)
values ('Alan', 'Fresco', 'afresc0', 'password');
insert into trainer (fname, lname, username, password)
values ('Guy', 'Mann', 'daGmann', 'football');
insert into trainer (fname, lname, username, password)
values ('Dianne', 'Ameter', 'diameter', 'trustno1');

/* insert users and plans */
insert into plan (id, trainer_id, calories, protein, carbs, fat, water)
values (2, 2, 2500, 20, 25, 5, 8);
insert into user (fname, lname, username, password, trainer_id, plan_id)
values ('John', 'Smith', 'phoneguy', '12223334444', 2, 2);
insert into plan (id, trainer_id, calories, protein, carbs, fat, water)
values (3, 2, 1800, 13, 8, 5, 6);
insert into user (fname, lname, username, password, trainer_id, plan_id)
values ('Jane', 'Doe', 'lostdoe2', 'someword5', 2, 3);
insert into user (fname, lname, username, password, trainer_id, plan_id)
values ('Richard', 'Tea', 'rtea135', 'd0nth4ckm3', 1, 1);
insert into user (fname, lname, username, password, trainer_id, plan_id)
values ('Damian', 'Roy', 'dRoyJenkins', 'superman', 1, 1);
insert into user (fname, lname, username, password, trainer_id, plan_id)
values ('Victoria', 'Holloway', 'holloway7', 'princess', 1, 1);
insert into user (fname, lname, username, password, trainer_id, plan_id)
values ('Johnathan', 'Humphrey', 'jofree', 'computer', 1, 1);
insert into user (fname, lname, username, password, trainer_id, plan_id)
values ('Charlotte', 'Downs', 'cdowns22', 'qwertyui', 1, 1);
insert into user (fname, lname, username, password, trainer_id, plan_id)
values ('Juan', 'Washington', 'jwash13', 'mercedes', 1, 1);
insert into user (fname, lname, username, password, trainer_id, plan_id)
values ('Ricardo', 'Dejesus', 'ric4rd0', 'firebird', 3, 1);
insert into user (fname, lname, username, password, trainer_id, plan_id)
values ('Mary', 'Norton', 'mnorton', 'mountain', 1, 1);

/* insert diary entries */
insert into diet_diary (entry_date, food, calories, protein, carbs, fat, quantity, food_id, user_id)
values ('2016-12-04', 'Turkey Breast', 29, 1, 0, 0, 4, (select id from food_db where name = 'Turkey Breast'), (select id from user where username = 'phoneguy'));
insert into diet_diary (entry_date, food, calories, protein, carbs, fat, quantity, food_id, user_id)
values ('2016-12-04', 'Chicken Breast', 35, 1, 0, 0, 8, (select id from food_db where name = 'Chicken Breast'), (select id from user where username = 'phoneguy'));
insert into diet_diary (entry_date, food, calories, protein, carbs, fat, quantity, food_id, user_id)
values ('2016-12-04', '1% Milk', 103, 1, 1, 0, 2, (select id from food_db where name = '1% Milk'), (select id from user where username = 'phoneguy'));
insert into diet_diary (entry_date, food, calories, protein, carbs, fat, quantity, food_id, user_id)
values ('2016-12-04', 'Apple', 36, 0, 1, 0, 2, (select id from food_db where name = 'Apple'), (select id from user where username = 'phoneguy'));
insert into water_diary (entry_date, quantity, user_id)
values ('2016-12-04', 3, (select id from user where username = 'phoneguy'));
insert into water_diary (entry_date, quantity, user_id)
values ('2016-12-05', 2, (select id from user where username = 'phoneguy'));
insert into diet_diary (entry_date, food, calories, protein, carbs, fat, quantity, food_id, user_id)
values ('2016-12-05', 'Apple', 36, 0, 1, 0, 1, (select id from food_db where name = 'Apple'), (select id from user where username = 'lostdoe2'));
insert into water_diary (entry_date, quantity, user_id)
values ('2016-12-05', 1, (select id from user where username = 'lostdoe2'));