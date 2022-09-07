--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5 (Ubuntu 14.5-1.pgdg20.04+1)
-- Dumped by pg_dump version 14.5 (Ubuntu 14.5-1.pgdg20.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: departments; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.departments (
    id integer NOT NULL,
    is_active boolean,
    description text,
    name character varying(60) NOT NULL,
    boss_id integer,
    directorate_id integer
);


ALTER TABLE public.departments OWNER TO root;

--
-- Name: departments_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.departments_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.departments_id_seq OWNER TO root;

--
-- Name: departments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.departments_id_seq OWNED BY public.departments.id;


--
-- Name: directorates; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.directorates (
    id integer NOT NULL,
    is_active boolean,
    description text,
    name character varying(60) NOT NULL,
    director_id integer
);


ALTER TABLE public.directorates OWNER TO root;

--
-- Name: directorates_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.directorates_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.directorates_id_seq OWNER TO root;

--
-- Name: directorates_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.directorates_id_seq OWNED BY public.directorates.id;


--
-- Name: employees; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.employees (
    id integer NOT NULL,
    is_active boolean,
    egn character varying(10),
    age smallint,
    first_name character varying(30),
    last_name character varying(30),
    password character varying(255),
    "position" character varying(255),
    token character varying(255),
    username character varying(35),
    department_id integer
);


ALTER TABLE public.employees OWNER TO root;

--
-- Name: employees_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.employees_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.employees_id_seq OWNER TO root;

--
-- Name: employees_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.employees_id_seq OWNED BY public.employees.id;


--
-- Name: departments id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.departments ALTER COLUMN id SET DEFAULT nextval('public.departments_id_seq'::regclass);


--
-- Name: directorates id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.directorates ALTER COLUMN id SET DEFAULT nextval('public.directorates_id_seq'::regclass);


--
-- Name: employees id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.employees ALTER COLUMN id SET DEFAULT nextval('public.employees_id_seq'::regclass);


--
-- Data for Name: departments; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.departments (id, is_active, description, name, boss_id, directorate_id) FROM stdin;
1	t	This is department description.	Department 1	4	1
2	t	This is department description.	Department 2	4	1
3	t	This is department description.	Department 3	4	2
4	t	This is department description.	Department 4	4	3
\.


--
-- Data for Name: directorates; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.directorates (id, is_active, description, name, director_id) FROM stdin;
1	t	This is directorate description.	Directorate 1	2
2	t	This is directorate description.	Directorate 2	2
3	t	This is directorate description.	Directorate 3	9
\.


--
-- Data for Name: employees; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.employees (id, is_active, egn, age, first_name, last_name, password, "position", token, username, department_id) FROM stdin;
1	t	9999999999	21	admin	admin	parola	ADMIN	\N	admin	4
2	t	9999999999	24	Petar	Petrov	parola	DIRECTOR	\N	petar	1
3	t	9999999999	22	Ivan	Ivan	parola	EMPLOYEE	\N	ivan	2
4	t	9999999999	44	Dimitar	Dimitar	parola	BOSS	\N	dimitar	3
5	t	9999999999	21	Maria	Maria	parola	EMPLOYEE	\N	maria	1
6	t	9999999999	21	employee1	employee1	parola	EMPLOYEE	\N	employee1	1
7	t	9999999999	21	employee2	employee2	parola	EMPLOYEE	\N	employee2	2
8	t	9999999999	21	employee2	employee2	parola	EMPLOYEE	\N	employee3	2
9	t	9999999999	21	employee4	employee4	parola	DIRECTOR	\N	employee4	4
\.


--
-- Name: departments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.departments_id_seq', 4, true);


--
-- Name: directorates_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.directorates_id_seq', 3, true);


--
-- Name: employees_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.employees_id_seq', 9, true);


--
-- Name: departments departments_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT departments_pkey PRIMARY KEY (id);


--
-- Name: directorates directorates_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.directorates
    ADD CONSTRAINT directorates_pkey PRIMARY KEY (id);


--
-- Name: employees employees_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (id);


--
-- Name: directorates uk_isf5rjb14c0xjg8wrpl3gp0nm; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.directorates
    ADD CONSTRAINT uk_isf5rjb14c0xjg8wrpl3gp0nm UNIQUE (name);


--
-- Name: departments uk_j6cwks7xecs5jov19ro8ge3qk; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT uk_j6cwks7xecs5jov19ro8ge3qk UNIQUE (name);


--
-- Name: departments fkfolh8m8g8c0apqtoyggbnp17f; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT fkfolh8m8g8c0apqtoyggbnp17f FOREIGN KEY (directorate_id) REFERENCES public.directorates(id);


--
-- Name: employees fkgy4qe3dnqrm3ktd76sxp7n4c2; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT fkgy4qe3dnqrm3ktd76sxp7n4c2 FOREIGN KEY (department_id) REFERENCES public.departments(id);


--
-- Name: directorates fkl0dpj0kjahrrdvr361rri7oyv; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.directorates
    ADD CONSTRAINT fkl0dpj0kjahrrdvr361rri7oyv FOREIGN KEY (director_id) REFERENCES public.employees(id);


--
-- Name: departments fkreeyd2r7dfd3xl608y0em3fek; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.departments
    ADD CONSTRAINT fkreeyd2r7dfd3xl608y0em3fek FOREIGN KEY (boss_id) REFERENCES public.employees(id);


--
-- PostgreSQL database dump complete
--

