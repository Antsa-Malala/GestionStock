drop database stock;
CREATE DATABASE stock;
CREATE user stock WITH PASSWORD 'stock';
GRANT ALL PRIVILEGES ON DATABASE "stock" to stock;
ALTER DATABASE stock OWNER TO stock;

create table article(
    idArticle varchar(7) primary key,
    nomArticle varchar(100),
    typeSortie varchar(30)
);

create table Magasin(
    idMagasin varchar(7) primary key,
    nomMagasin varchar(30),
    ouverture time,
    fermeture time
);

create table unite(
    idunite serial primary key,
    nomUnite varchar(30),
    reference double precision,
    categorie varchar(10)
);
create table MouvementProvisoire(
    idMouvement serial primary key,
    dateMouvement date,
    idArticle varchar(7),
    quantite double precision,
    idUnite int,
    idMagasin varchar(7),
    foreign key(idArticle) references article(idArticle),
    foreign key(idUnite) references unite(idunite),
    foreign key(idMagasin) references Magasin(idMagasin)
);

create table MouvementInsere(
    idMouvement serial primary key,
    dateMouvement date,
    idArticle varchar(7),
    quantite double precision,
    idUnite int,
    pu int,
    idMagasin varchar(7),
    mouvement varchar(50),
    foreign key(idArticle) references article(idArticle),
    foreign key(idUnite) references unite(idunite),
    foreign key(idMagasin) references Magasin(idMagasin)
);

create table Mouvement(
    idMouvement serial primary key,
    dateMouvement date,
    idArticle varchar(7),
    quantite double precision,
    idUnite int,
    pu double precision,
    idMagasin varchar(7),
    idMouvementEntree int,
    idMouvementProvisoire int,
    foreign key(idMouvementProvisoire) references mouvementProvisoire(idmouvement),
    foreign key(idArticle) references article(idArticle),
    foreign key(idUnite) references unite(idunite),
    foreign key(idMagasin) references Magasin(idMagasin)
);


create table utilisateur(
    idutilisateur varchar(7) primary key,
    nomUtilisateur varchar(60),
    motDePasse varchar(60)
);

create table valide(
    datevalidation date,
    idutilisateur varchar(7),
    idmouvement int,
    foreign key(idmouvement) references mouvementProvisoire(idmouvement),
    foreign key(idutilisateur) references utilisateur(idutilisateur)
);

--------------------DONNEES TEST------------------------------------------------------------------
insert into article values('SRL1100','Biscuit','FIFO');
insert into article values('SRL2100','THB','LIFO');
insert into article values('SRL1200','Fromage','LIFO');

insert into unite values(default,'unite',1,'SRL2100');
insert into unite values(default,'cageot',8,'SRL2100');

insert into unite values(default,'unite',1,'SRL1100');
insert into unite values(default,'sachet',50,'SRL1100');

insert into unite values(default,'unite',1,'SRL1200');
insert into unite values(default,'carton',10,'SRL1200');


insert into magasin values('MGS0001','Magasin Anosy','08:00:00','18:00:00');
insert into magasin values('MGS0002','Magasin Analakely','07:00:00','17:00:00');
insert into magasin values('MGS0003','Magasin Behoririka','07:00:00','18:00:00');

insert into utilisateur values('USR0001','antsamalala@gmail.com','pass1');
insert into utilisateur values('USR0002','malalanirina@gmail.com','pass2');
insert into utilisateur values('USR0003','antsa@gmail.com','pass3');


create view sortie as 
select * 
from mouvement 
where idmouvemententree is not null and idMouvementProvisoire is not null;

create view sortieType as 
select sortie.*,article.typeSortie
from sortie
join article on sortie.idArticle=article.idArticle;

create view entree as 
select * 
from mouvement 
where idmouvemententree is null and idMouvementProvisoire is null;

create view reste as 
select entree.idmouvement,entree.quantite,entree.datemouvement,entree.idarticle,entree.idunite,entree.pu,entree.idmagasin,sortie.idmouvemententree,
entree.quantite-sum(sortie.quantite) as reste 
from sortie 
join entree on entree.idMouvement=sortie.idmouvemententree 
group by sortie.idmouvemententree,
entree.idmouvement,entree.quantite,entree.datemouvement,entree.idarticle,entree.idunite,entree.pu,entree.idmagasin;

create view MouvementValidation as
select mp.*,v.datevalidation,v.idutilisateur 
from mouvementProvisoire as mp 
left join valide as v on v.idmouvement=mp.idmouvement;

create view MouvementValide as 
select * 
from MouvementValidation 
where dateValidation is not null;

create view MouvementInvalide as 
select * 
from MouvementValidation 
where dateValidation is null;