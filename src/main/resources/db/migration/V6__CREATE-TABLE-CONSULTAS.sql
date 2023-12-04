create table consultas(
    id serial not null,
    id_paciente integer not null,
    id_medico integer not null,
    data_consulta timestamp not null,

    primary key (id),

    constraint fk_consultas_pacientes foreign key (id_paciente)
        references pacientes (id),
    constraint fk_consultas_medicos foreign key (id_medico)
        references medicos (id)
);