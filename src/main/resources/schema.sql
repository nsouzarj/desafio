create table funcionario
(
 id integer  not null,
 nomeFuncionario varchar(150),
 funcaoFuncionario varchar(100),
 empresaFuncionario varchar(100),
 primary key (id)
);

create table embarque
(
  id integer not null,
  funcionario_id_funcionario integer not null,
  dataEmbarque date,
  dataDesenbarque date,
  dias_maximo_embarcado  integer,
  dias_minimo_de_folga integer,

  primary key (id)
);