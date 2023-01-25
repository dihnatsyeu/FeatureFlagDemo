drop table if exists feature_flags;
create table feature_flags (
                               id bigint not null,
                               account varchar(255),
                               is_enabled boolean default false,
                               is_global boolean not null,
                               name varchar(255),
                               primary key (id)
);

insert into feature_flags (id, account, is_enabled, is_global, name) values
    (1,'user1', true, false, 'Test feature 1');
insert into feature_flags (id, account, is_enabled, is_global, name) values
    (2,'user1', true, true, 'Test feature 2');
insert into feature_flags (id, account, is_enabled, is_global, name) values
    (3,'user1', false, false, 'Test feature 3');
insert into feature_flags (id, account, is_enabled, is_global, name) values
    (4,'user2', true, false, 'Test feature 4');