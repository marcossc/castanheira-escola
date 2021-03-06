PGDMP     -                    v            teste    9.6.2    10.1 F    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3            �            1259    16515    aluno_id_seq    SEQUENCE     n   CREATE SEQUENCE aluno_id_seq
    START WITH 3
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.aluno_id_seq;
       public       postgres    false    3            �            1259    16395    aluno    TABLE     k  CREATE TABLE aluno (
    id bigint DEFAULT nextval('aluno_id_seq'::regclass) NOT NULL,
    nome character varying(100) NOT NULL,
    dt_nascimento date NOT NULL,
    id_ano integer,
    laudado boolean,
    situacao character(1),
    CONSTRAINT ckc_situacao_aluno CHECK (((situacao IS NULL) OR (situacao = ANY (ARRAY['A'::bpchar, 'T'::bpchar, 'E'::bpchar]))))
);
    DROP TABLE public.aluno;
       public         postgres    false    196    3            �            1259    16513    aluno_id    SEQUENCE     j   CREATE SEQUENCE aluno_id
    START WITH 3
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.aluno_id;
       public       postgres    false    3            �            1259    16518 
   ano_id_seq    SEQUENCE     m   CREATE SEQUENCE ano_id_seq
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.ano_id_seq;
       public       postgres    false    3            �            1259    16403    ano    TABLE     p   CREATE TABLE ano (
    id integer DEFAULT nextval('ano_id_seq'::regclass) NOT NULL,
    ano integer NOT NULL
);
    DROP TABLE public.ano;
       public         postgres    false    197    3            �            1259    16520    boletim_id_seq    SEQUENCE     q   CREATE SEQUENCE boletim_id_seq
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.boletim_id_seq;
       public       postgres    false    3            �            1259    16410    boletim    TABLE     z  CREATE TABLE boletim (
    id bigint DEFAULT nextval('boletim_id_seq'::regclass) NOT NULL,
    id_matricula bigint NOT NULL,
    id_disciplina integer NOT NULL,
    nota_1 smallint,
    falta_1 integer,
    nota_2 smallint,
    falta_2 integer,
    nota_3 smallint,
    falta_3 integer,
    media smallint,
    total_falta integer,
    CONSTRAINT ckc_nota_1_boletim CHECK (((nota_1 IS NULL) OR ((nota_1 >= 0) AND (nota_1 <= 30)))),
    CONSTRAINT ckc_nota_2_boletim CHECK (((nota_2 IS NULL) OR ((nota_2 >= 0) AND (nota_2 <= 30)))),
    CONSTRAINT ckc_nota_3_boletim CHECK (((nota_3 IS NULL) OR ((nota_3 >= 0) AND (nota_3 <= 40))))
);
    DROP TABLE public.boletim;
       public         postgres    false    198    3            �            1259    16522    disciplina_id_seq    SEQUENCE     t   CREATE SEQUENCE disciplina_id_seq
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.disciplina_id_seq;
       public       postgres    false    3            �            1259    16420 
   disciplina    TABLE     �   CREATE TABLE disciplina (
    id integer DEFAULT nextval('disciplina_id_seq'::regclass) NOT NULL,
    nome character varying(30)
);
    DROP TABLE public.disciplina;
       public         postgres    false    199    3            �            1259    16524    disciplina_ano_id_seq    SEQUENCE     x   CREATE SEQUENCE disciplina_ano_id_seq
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.disciplina_ano_id_seq;
       public       postgres    false    3            �            1259    16427    disciplina_ano    TABLE     �   CREATE TABLE disciplina_ano (
    id integer DEFAULT nextval('disciplina_ano_id_seq'::regclass) NOT NULL,
    id_disciplina integer,
    id_ano integer
);
 "   DROP TABLE public.disciplina_ano;
       public         postgres    false    200    3            �            1259    16526    matricula_id_seq    SEQUENCE     s   CREATE SEQUENCE matricula_id_seq
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.matricula_id_seq;
       public       postgres    false    3            �            1259    16434 	   matricula    TABLE     �   CREATE TABLE matricula (
    id bigint DEFAULT nextval('matricula_id_seq'::regclass) NOT NULL,
    id_aluno bigint,
    id_turma bigint,
    aprovado boolean
);
    DROP TABLE public.matricula;
       public         postgres    false    201    3            �            1259    16528    professor_id_seq    SEQUENCE     s   CREATE SEQUENCE professor_id_seq
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.professor_id_seq;
       public       postgres    false    3            �            1259    16439 	   professor    TABLE     �  CREATE TABLE professor (
    id integer DEFAULT nextval('professor_id_seq'::regclass) NOT NULL,
    nome character varying(100) NOT NULL,
    usuario character varying(255) NOT NULL,
    senha character varying(255) NOT NULL,
    tipo character(1),
    email character varying(255),
    CONSTRAINT ckc_tipo_professo CHECK (((tipo IS NULL) OR (tipo = ANY (ARRAY['P'::bpchar, 'S'::bpchar, 'D'::bpchar]))))
);
    DROP TABLE public.professor;
       public         postgres    false    202    3            �            1259    16530    professor_disc_tur_id_seq    SEQUENCE     |   CREATE SEQUENCE professor_disc_tur_id_seq
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.professor_disc_tur_id_seq;
       public       postgres    false    3            �            1259    16446    professor_disc_tur    TABLE     �   CREATE TABLE professor_disc_tur (
    id integer DEFAULT nextval('professor_disc_tur_id_seq'::regclass) NOT NULL,
    id_professor integer,
    id_disciplina integer,
    id_turma integer
);
 &   DROP TABLE public.professor_disc_tur;
       public         postgres    false    203    3            �            1259    16532    turma_id_seq    SEQUENCE     o   CREATE SEQUENCE turma_id_seq
    START WITH 10
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.turma_id_seq;
       public       postgres    false    3            �            1259    16451    turma    TABLE     �   CREATE TABLE turma (
    id integer DEFAULT nextval('turma_id_seq'::regclass) NOT NULL,
    id_ano integer NOT NULL,
    nome character varying(10) NOT NULL
);
    DROP TABLE public.turma;
       public         postgres    false    204    3            �          0    16395    aluno 
   TABLE DATA               L   COPY aluno (id, nome, dt_nascimento, id_ano, laudado, situacao) FROM stdin;
    public       postgres    false    186   �Q       �          0    16403    ano 
   TABLE DATA                  COPY ano (id, ano) FROM stdin;
    public       postgres    false    187   *z       �          0    16410    boletim 
   TABLE DATA               �   COPY boletim (id, id_matricula, id_disciplina, nota_1, falta_1, nota_2, falta_2, nota_3, falta_3, media, total_falta) FROM stdin;
    public       postgres    false    188   fz       �          0    16420 
   disciplina 
   TABLE DATA               '   COPY disciplina (id, nome) FROM stdin;
    public       postgres    false    189   �       �          0    16427    disciplina_ano 
   TABLE DATA               <   COPY disciplina_ano (id, id_disciplina, id_ano) FROM stdin;
    public       postgres    false    190   1�       �          0    16434 	   matricula 
   TABLE DATA               >   COPY matricula (id, id_aluno, id_turma, aprovado) FROM stdin;
    public       postgres    false    191   ��       �          0    16439 	   professor 
   TABLE DATA               C   COPY professor (id, nome, usuario, senha, tipo, email) FROM stdin;
    public       postgres    false    192   `�       �          0    16446    professor_disc_tur 
   TABLE DATA               P   COPY professor_disc_tur (id, id_professor, id_disciplina, id_turma) FROM stdin;
    public       postgres    false    193   ��       �          0    16451    turma 
   TABLE DATA               *   COPY turma (id, id_ano, nome) FROM stdin;
    public       postgres    false    194   k�       �           0    0    aluno_id    SEQUENCE SET     0   SELECT pg_catalog.setval('aluno_id', 3, false);
            public       postgres    false    195            �           0    0    aluno_id_seq    SEQUENCE SET     6   SELECT pg_catalog.setval('aluno_id_seq', 1303, true);
            public       postgres    false    196            �           0    0 
   ano_id_seq    SEQUENCE SET     2   SELECT pg_catalog.setval('ano_id_seq', 11, true);
            public       postgres    false    197            �           0    0    boletim_id_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('boletim_id_seq', 292, true);
            public       postgres    false    198            �           0    0    disciplina_ano_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('disciplina_ano_id_seq', 48, true);
            public       postgres    false    200            �           0    0    disciplina_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('disciplina_id_seq', 20, true);
            public       postgres    false    199            �           0    0    matricula_id_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('matricula_id_seq', 50, true);
            public       postgres    false    201            �           0    0    professor_disc_tur_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('professor_disc_tur_id_seq', 126, true);
            public       postgres    false    203            �           0    0    professor_id_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('professor_id_seq', 94, true);
            public       postgres    false    202            �           0    0    turma_id_seq    SEQUENCE SET     4   SELECT pg_catalog.setval('turma_id_seq', 32, true);
            public       postgres    false    204                       2606    16409    ano ak_ak_ano_ano 
   CONSTRAINT     D   ALTER TABLE ONLY ano
    ADD CONSTRAINT ak_ak_ano_ano UNIQUE (ano);
 ;   ALTER TABLE ONLY public.ano DROP CONSTRAINT ak_ak_ano_ano;
       public         postgres    false    187            $           2606    16433 ,   disciplina_ano ak_ak_disciplina_ano_discipli 
   CONSTRAINT     q   ALTER TABLE ONLY disciplina_ano
    ADD CONSTRAINT ak_ak_disciplina_ano_discipli UNIQUE (id_disciplina, id_ano);
 V   ALTER TABLE ONLY public.disciplina_ano DROP CONSTRAINT ak_ak_disciplina_ano_discipli;
       public         postgres    false    190    190            *           2606    16559    professor ak_ak_email_professo 
   CONSTRAINT     S   ALTER TABLE ONLY professor
    ADD CONSTRAINT ak_ak_email_professo UNIQUE (email);
 H   ALTER TABLE ONLY public.professor DROP CONSTRAINT ak_ak_email_professo;
       public         postgres    false    192                       2606    16419 %   boletim ak_ak_matricula_disci_boletim 
   CONSTRAINT     p   ALTER TABLE ONLY boletim
    ADD CONSTRAINT ak_ak_matricula_disci_boletim UNIQUE (id_matricula, id_disciplina);
 O   ALTER TABLE ONLY public.boletim DROP CONSTRAINT ak_ak_matricula_disci_boletim;
       public         postgres    false    188    188                        2606    16426    disciplina ak_ak_nome_discipli 
   CONSTRAINT     R   ALTER TABLE ONLY disciplina
    ADD CONSTRAINT ak_ak_nome_discipli UNIQUE (nome);
 H   ALTER TABLE ONLY public.disciplina DROP CONSTRAINT ak_ak_nome_discipli;
       public         postgres    false    189                       2606    16402    aluno ak_ak_nome_dt_nasc_aluno 
   CONSTRAINT     a   ALTER TABLE ONLY aluno
    ADD CONSTRAINT ak_ak_nome_dt_nasc_aluno UNIQUE (nome, dt_nascimento);
 H   ALTER TABLE ONLY public.aluno DROP CONSTRAINT ak_ak_nome_dt_nasc_aluno;
       public         postgres    false    186    186            2           2606    16457    turma ak_ak_nome_turma 
   CONSTRAINT     J   ALTER TABLE ONLY turma
    ADD CONSTRAINT ak_ak_nome_turma UNIQUE (nome);
 @   ALTER TABLE ONLY public.turma DROP CONSTRAINT ak_ak_nome_turma;
       public         postgres    false    194            ,           2606    16552    professor ak_usuario 
   CONSTRAINT     K   ALTER TABLE ONLY professor
    ADD CONSTRAINT ak_usuario UNIQUE (usuario);
 >   ALTER TABLE ONLY public.professor DROP CONSTRAINT ak_usuario;
       public         postgres    false    192                       2606    16400    aluno pk_aluno 
   CONSTRAINT     E   ALTER TABLE ONLY aluno
    ADD CONSTRAINT pk_aluno PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.aluno DROP CONSTRAINT pk_aluno;
       public         postgres    false    186                       2606    16407 
   ano pk_ano 
   CONSTRAINT     A   ALTER TABLE ONLY ano
    ADD CONSTRAINT pk_ano PRIMARY KEY (id);
 4   ALTER TABLE ONLY public.ano DROP CONSTRAINT pk_ano;
       public         postgres    false    187                       2606    16417    boletim pk_boletim 
   CONSTRAINT     I   ALTER TABLE ONLY boletim
    ADD CONSTRAINT pk_boletim PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.boletim DROP CONSTRAINT pk_boletim;
       public         postgres    false    188            "           2606    16424    disciplina pk_disciplina 
   CONSTRAINT     O   ALTER TABLE ONLY disciplina
    ADD CONSTRAINT pk_disciplina PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.disciplina DROP CONSTRAINT pk_disciplina;
       public         postgres    false    189            &           2606    16431     disciplina_ano pk_disciplina_ano 
   CONSTRAINT     W   ALTER TABLE ONLY disciplina_ano
    ADD CONSTRAINT pk_disciplina_ano PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.disciplina_ano DROP CONSTRAINT pk_disciplina_ano;
       public         postgres    false    190            (           2606    16438    matricula pk_matricula 
   CONSTRAINT     M   ALTER TABLE ONLY matricula
    ADD CONSTRAINT pk_matricula PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.matricula DROP CONSTRAINT pk_matricula;
       public         postgres    false    191            .           2606    16443    professor pk_professor 
   CONSTRAINT     M   ALTER TABLE ONLY professor
    ADD CONSTRAINT pk_professor PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.professor DROP CONSTRAINT pk_professor;
       public         postgres    false    192            0           2606    16450 (   professor_disc_tur pk_professor_disc_tur 
   CONSTRAINT     _   ALTER TABLE ONLY professor_disc_tur
    ADD CONSTRAINT pk_professor_disc_tur PRIMARY KEY (id);
 R   ALTER TABLE ONLY public.professor_disc_tur DROP CONSTRAINT pk_professor_disc_tur;
       public         postgres    false    193            4           2606    16455    turma pk_turma 
   CONSTRAINT     E   ALTER TABLE ONLY turma
    ADD CONSTRAINT pk_turma PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.turma DROP CONSTRAINT pk_turma;
       public         postgres    false    194            5           2606    16458    aluno fk_aluno_ano    FK CONSTRAINT     �   ALTER TABLE ONLY aluno
    ADD CONSTRAINT fk_aluno_ano FOREIGN KEY (id_ano) REFERENCES ano(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
 <   ALTER TABLE ONLY public.aluno DROP CONSTRAINT fk_aluno_ano;
       public       postgres    false    186    187    2074            :           2606    16483    matricula fk_aluno_matricula    FK CONSTRAINT     �   ALTER TABLE ONLY matricula
    ADD CONSTRAINT fk_aluno_matricula FOREIGN KEY (id_aluno) REFERENCES aluno(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
 F   ALTER TABLE ONLY public.matricula DROP CONSTRAINT fk_aluno_matricula;
       public       postgres    false    2070    186    191            8           2606    16473     disciplina_ano fk_ano_disciplina    FK CONSTRAINT     �   ALTER TABLE ONLY disciplina_ano
    ADD CONSTRAINT fk_ano_disciplina FOREIGN KEY (id_ano) REFERENCES ano(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
 J   ALTER TABLE ONLY public.disciplina_ano DROP CONSTRAINT fk_ano_disciplina;
       public       postgres    false    2074    187    190            9           2606    16478     disciplina_ano fk_disciplina_ano    FK CONSTRAINT     �   ALTER TABLE ONLY disciplina_ano
    ADD CONSTRAINT fk_disciplina_ano FOREIGN KEY (id_disciplina) REFERENCES disciplina(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
 J   ALTER TABLE ONLY public.disciplina_ano DROP CONSTRAINT fk_disciplina_ano;
       public       postgres    false    190    2082    189            6           2606    16463    boletim fk_disciplina_boletim    FK CONSTRAINT     �   ALTER TABLE ONLY boletim
    ADD CONSTRAINT fk_disciplina_boletim FOREIGN KEY (id_disciplina) REFERENCES disciplina(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
 G   ALTER TABLE ONLY public.boletim DROP CONSTRAINT fk_disciplina_boletim;
       public       postgres    false    2082    188    189            7           2606    16468    boletim fk_matricula_boletim    FK CONSTRAINT     �   ALTER TABLE ONLY boletim
    ADD CONSTRAINT fk_matricula_boletim FOREIGN KEY (id_matricula) REFERENCES matricula(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
 F   ALTER TABLE ONLY public.boletim DROP CONSTRAINT fk_matricula_boletim;
       public       postgres    false    188    2088    191            <           2606    16493 (   professor_disc_tur fk_prof_disc_tur_disc    FK CONSTRAINT     �   ALTER TABLE ONLY professor_disc_tur
    ADD CONSTRAINT fk_prof_disc_tur_disc FOREIGN KEY (id_disciplina) REFERENCES disciplina(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
 R   ALTER TABLE ONLY public.professor_disc_tur DROP CONSTRAINT fk_prof_disc_tur_disc;
       public       postgres    false    2082    189    193            =           2606    16498 -   professor_disc_tur fk_prof_disc_tur_professor    FK CONSTRAINT     �   ALTER TABLE ONLY professor_disc_tur
    ADD CONSTRAINT fk_prof_disc_tur_professor FOREIGN KEY (id_professor) REFERENCES professor(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
 W   ALTER TABLE ONLY public.professor_disc_tur DROP CONSTRAINT fk_prof_disc_tur_professor;
       public       postgres    false    2094    192    193            >           2606    16503 '   professor_disc_tur fk_prof_disc_tur_tur    FK CONSTRAINT     �   ALTER TABLE ONLY professor_disc_tur
    ADD CONSTRAINT fk_prof_disc_tur_tur FOREIGN KEY (id_turma) REFERENCES turma(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
 Q   ALTER TABLE ONLY public.professor_disc_tur DROP CONSTRAINT fk_prof_disc_tur_tur;
       public       postgres    false    2100    194    193            ?           2606    16508    turma fk_turma_ano    FK CONSTRAINT     �   ALTER TABLE ONLY turma
    ADD CONSTRAINT fk_turma_ano FOREIGN KEY (id_ano) REFERENCES ano(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
 <   ALTER TABLE ONLY public.turma DROP CONSTRAINT fk_turma_ano;
       public       postgres    false    187    2074    194            ;           2606    16488    matricula fk_turma_matricula    FK CONSTRAINT     �   ALTER TABLE ONLY matricula
    ADD CONSTRAINT fk_turma_matricula FOREIGN KEY (id_turma) REFERENCES turma(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
 F   ALTER TABLE ONLY public.matricula DROP CONSTRAINT fk_turma_matricula;
       public       postgres    false    2100    194    191            �      x��}Ks�ؕ���ԑxKR��H�T�����H�Ffʕ���F��Z�̦��csν�dj<�]v������ΓE^Eg�};.����n�vq���a%�e�fY�Y�Q}���T�ut�un�����0�ǽ_���lWn��2\U���7ˌW��l����]���~��Z��e�&.�6fڏ���6��C��_M�]�'_N���a|����rM�.`��7H��Y�o�Z���hV��m3��Î�_��:o��Y�����>�vt۝��ߴ#R���hU�&Yʪ<�h�;8j�7z���������rYSD��p�f�s羇_�/��$zQe�nt���m�}��M�_N���lE]�c�:����$�u���G����a���*�1���2���q�`�nq1�}�ᡧ�����(������a���pe3���k3[:r�&�I�q��[����/�(��؄��)����#�]��~��+��޳�z��aq���a׷�νn�~�V�D��ѕ�����"�nz�l��i��@�����Ǧ#a��J	�#|���� r�L.�-3�UV�%�U����v��%�Pְb�n���9p���f��3Ӗ�F&�%P�:.��=P��n���c������n����@j@֝a�Ņk�@@ua}�M5������ ����_)�	�4�i�a!l
b���8�{�ѥ\S�ђO�fpxP.ﶫ��8��ȣ[P�W����?�K(�C~�uE�e�ǁD e�s���qG
	4�诪��:�*���������a���J���~�{»m�m�pA�yO$��ij�f�`?�Pm�&�_V��(�$�
�V?���//ᗊ����b�?v�~���]������W�'oR'3���O\7+�]�\��:������\��?�P����E�#\RgD=,��;d+6Z*�����	0m��F��[X㭒.�`����vͨ���eY�3u�������7s	�����~�bk�e=��Ƴ�Y�i��ɳ�Y�v�έ�h��B[���$�)�K28(Bj%:0�)�<�8z��s�̓�u�:U�I"������;�����*�#}0/>��*��BV J}2�}rzE��̘)ڊ=GЭ��������_���t�{~���Sׂ��߀EK���̔'od��7�B˪2�����s��8o�� �p�j�e��ܴ���YC4�����������e�y95��w�jܶ��+�qX?��,�x&�	��0+Hp��l%�b�k�Ud?AL�F�A��%��* �����7�<�vO�6��y��hS�T���G⯯��3Vɣ"�sP���*��f�֛��s�?=���X�%vGUt���L��`dF�:
JiX��T�X��.	x�vxrM�hr5^�%��;TM]y�%��fߎ�6z� �wk`[o��/-��F[1���5w�3X:��C
�.Ab�4p%�����*^ρ��I�6'ヘ����j`�2XhY��a}��z/e���]�m�~�u;n�nQ�6[�$���8J�[� �W'���jvb�V����4�r*��8Iw���k��~q9��v�l#��T<�����^,M��_�G�=������}�� }��Ќ��z��ri��-n�s*U�3�,��ʙL̀z*tA��Hp��va�C��� �ﴌ.\ۻo �+l4-�B���L+q��:��+P��n�R�ZH�/����?^�ܩ�Yʎ�����337�6&/�zخ]�0�:?��ތ���2K��������(x.9@�2��a�e����cE���_fYD[Lڸq�J8x����#H��\7+��n����[�-���D#�cY��ޠ�@
W�w��EV@��;|(w3�s��YMX ���ݠ�\����ݎMp
�n��8���GK ��K��E쭿:$�9��-��_ZpV.�i�Q-�+r�k�nѽ��`Lnd��K�{nn ��#��+������Ɨ��ڭz�o��~�&"RP[{x�QYU�!/���<��<�g�D�v�#�.� �a����`W-�Ę(��+��/���{16�53��a+�8���7�e2�
��$�`�T�=)�\O��*G���=|  ��C�4o!��}�$}S݄�_�t���!H$�^�?�` ���w����Ev
Է�1�	��P�,T��GѲ톽@�ɖk�L�?���ہ7	Kv�!5 �2z����P (�* J�Hc�����C�P��<��h���F�2e�������>I��µeY�A��n�!���eN��I���n� �`סӥHޣ���i7x��0�'�|۽��p���<v�􂪰�5/��L��_�o�2���4O����M�0�]�"��j�D\�����ߏ!XbTZ�>ͮ�q�~�;����tU*A4r�g��K?����^I��_SMS��
�0X����f�,�2oQ>h6؇�/�%�@"T��tV%���a�Ɵ@��B�+�o�L���'d����gmTƝ>��єa4Ȫ�:b�U��5�s:�u��GlV�k�S\�F��l7����8ܫo+LYgѿ�G�|�6�Y�.�92"��Z�z\������0Q�� �
���41>dI�et���S�� �E���v���)��'���`�����_�������#���b���K�!�����)C�`��Ӿ7M��3 8&���N"��y�b]��ٙ]%�i�_�2�fW)���[��n���7ޡ1(}\�?�G��-1���a�� }����u����k��޴��_ �ɭ�M[�{�����G�U|s!V��f�D���� �9�)lִ���}BTeþ�~K����'��=)�]
3�w9{���?�mPZW���p};���{��@fפhj^��2>oJ�>A63n�7��>�?>�|�F�;�aч��)�k�Kǚ�8(h�Ƈ�v?�)����Ut�k�5!�v���K:��AL/M�ڣao�o۾٩1>�1>�J�@��0�p>�����Ю��D����~��q�UHz �6_1Gf��s��% L�?��U����u��Ī���Od�Y���v�1��@NF���~�xhT���������7=N��=��&,����=.	�D��D	��?�\4�H�vq���]�'�c��L� TQw����i`G[��f�7�:��^�?���Qu�&�EO� �W�Z�`�����N��!O�# �{պi���3
�\��{~2#W�Y?�J�a���j7��!ћ)��
<��۰x�e4SL
�ŐE#!~W�&*6��m�zܵ�ac_m�<��}}^�Z� �p$l�-��k�"��\xI�j�:�>!+4��A�U��x�v�P�"K�OX�p�ڋ�o�_��Z"���䚄4��q�?���dQh2(���x������B�ն!�8�-Z,⩛aܙ���0�n����㍤zʬ�,	G�����lE��z��Ǩ~�\H����j(g55K��LG�ͷ��	|�K M"NQ^;Gc���W����\Uy�!�e���B�<cZzk`���`�|�ܮ�\l_���fK��T�����Ԥ�c�%G3���[OP>k'*[�m^Eo=�I�Vv�����q�>�<d^{���м4�9���*�ʗw*�������ج	�u��gn�~�D�T���:Hk��(m�+�p7���L��-ja v�R���{�R�6��a�]IQEFQ��T�S��=��x�o7�ZW�X`:u�|�uA1~�@~��jǘ���� ���?o�m�/7���|��С���Бk{�����?z7��
 E�T.�^H�|����MeL���m`�����m����˗Pj��$���c�?͙Ϻ���)Ss�����#����x$�J�B�/1�:����D?�2�[����4໎���~��BY���œ �_�JxYE�����{T��t:.0�_'�j�HL� 4���Co`��Sʟ��8���4gne�ĨRٓE,��rU��:����4��3��M�p�I��W9��;��F    <���T&� �L�&��Ǫc��X��a�4ˬ�_a��{ӡ����[�r�lT5��vDTěWͨ�T$�^�>� �ʟ�yS
�u�d��f��@����:�/�F�P���4� ���� �t*@�M�q�q�=:��<=�V����?;��t*�5���ʐL�$��QГ�a]����GWg�*�}mh=�q�7�Ĉ�dƈ�\>���X�0PF��r���n����zO5��g�1��V}���IZհ9�k�Y���׿�B�T�_�jѤaʌ2$L�1�;�{?�%�9� �AAN(�y��@y@U* �O�Od��5!�p��݁#��X5t�Է�gY��lG�Ez5�Ro���k	������C���S�>�������όɮQ�W�"a�	Vw76'li,��֔_���?��#��R�2���ߩ�}�R��ɔ�c�!���T>(Rǅ���2����+�?Q*��h�J@2�PV�Y�s���5��Zp�*Y��=���m^�����ksK��n�[wb��>��Ԉ�Q7*��=jK�֑�%��>���8�k��o  ><w��)�f�\�J�*�x��k�p�g����,�0 �y����*�a�"��`VB�𴛑Si./#<l
Uo��?�� ������B��|�s�޵��,)Vbq�u���ه}0S/<�O d��2���޵��3�&i��S�ZF)$��E^�y�0�ԩ�{��rMiJ�n�=֌n*�g{�ңU¼$��x��e�T����`�/Xw���zdc��iܷ݁�D�L��3K���\�L�����嘾B�
|Ǖ-Ӑ�Պ�)�����2��-E�߸�3b1=9��4��Y�����������,�I��B�q��Dp������"4���Ӡm�YPҠ~ؕ�4<?+�[�r�k ��B�/��,��/C�u���`F��Lzu~��2�pL]D�=_��U�+04�\s�u�v���$�T�K����ZU�$�e���?��{��8�l�Hdŗ�~�������ʩyʸ��NݏW	z��'6-��>`[��������ay���6Z��r�$���<�w��b���]�M�.9I�ZHxgb�'Q{�W�� H+��~�|ߧ}�izݙ����f��&�lj+
_0(�ې�{>V��9D�
i�!�
a;��*q�,�w�7\yK�tܬ� %��פ�pA�,���`SE��Yd�]7�S5(�u�[բ6�ȣ��r=vt<L`��zQX BMT&)0Eټ ��ͻW%�Ϯ����4��q54(�[ԑ�	\O)�Nd咣� ����TrC����_�Gl�P�a�֤�<���rpT'v�HLV�������a����K���(��O�D������n[�_A/�Q!
���sK\rmܤ�!�r^&�"�
E��{ػ~�R)kM0	�,J�H̽U������@#s�g�c@s>��W%�܏}D��l
��t�%���!�h_$R��Ra�鶝�N\A{�UN�܉�6n�D%t5�n
�>Q�����C�H!�lBa^:��U�q��p]ﳦ����o�y�Ʃ����A�y�k��Wk�J}܃C}�j��R������l}B��`���}|n
۬���L�΢kP�� 6��
|���5�E3����K�J��k�C���[M8����;�m�k���jة��K�<�V������pNHXu��M��~�+�{9�g�Oq�vkwQ{�x��m��- ��m�н)�k�!mYz5&E�������D"�R��S~X2�&����i(_�"�q�}���s�Lz6},��hϱxPYΜ'�v@~�F�����#����ܳ��=����!�#?�S��	D�	�A�+"���
�U>r9~o�5'�Xʲ�5�'l��mXzQѭÓkI�-uėqt���z-��e���ύ�|�bA��_Ɨ��i��} "�C/���eI*)9N䣿�j�B����g�m��CB��� @2v�S$��M�N[���>c��{�}![�������X�� ���~�a+OJ��6�,��RH��P��o7l�)�p���d���ZL�ǋ�aЇ�A�l@�d�)��5Z�ߞ���=��IJ��Z�TGhpR�<�W�5| /'g�E����!������ݬ���W����e$5Xsٍ�RH8e7H�y��%UN.�Fܿ���|������}�[�:H�lQ��YG_�l�C<�z
e�=��4�EvΘgK�Rr,��V�y'�
��MU1�՚��r	��~�G{�-Y�˜��D���b�Զ�j��Qh"���%be���s��W��E��q�h1[���;�i��se�-9���<�#NI��g1/�P3��0$R���UIt�S��A�x� Y*�5� ��(�,���f��e���,��5<xR^�7�����̻d�A* �FÇY����&��!��'��d�dMmʝm������Kkp�v�_��/�i���P�����4��vf�6�y[����S1�y��Qf�r��� �"��i\�n�@Z��؜�.�M¾����cQB\��Gڅ����'�!�82�1��W)�X��5�n�[�C��oB7�E���/>R���Q�׍�t�VĲ�T�]L�.!GGit�4_L*��P�gi�Sl��?�zi��Z�^����!?�@�rR�b��Rѷ�U;>���.f��Ts��@@_��A3:��:�p��?���,+�����L����J�K�� #����feL�^���j'��+���r�-RH�3�g�]���)�#��(���R�q޾`yD��4�#ؒ�[�}��~�ow�~��]��6� ���t=�$�����ʦ��Y��}��(����P��C�34o[����'Y�ه�л�|���4�_P�7俅^[JK�T��Ĝ���a�O����B��>�Pm\8'&���	eVT(+��� @�P�L�Nh��I_]����}5�TO�4�A�Ӡ��B:���v�z~����p(v���,��a+�@���KQ��SP��7M3���ʈR�7��nbk� #� ��sxm�!��j���궽=���ނe�3I�����^�ќ��:�n������#U(��>���Tp�͸�_@Ej26Hx��sO�x���=7����~lG�������l�!6h"	��q��b�k��f(r�Ǔ��Jf7eP��MzT����;K�r��9z��b�v�ʣ�e�~��:'<3=/����[�(?�֬�,�IT�ĔL=%�EMm�>p���p�'�z�m��?Թ
u�a����Ú�B���Q�ོ�۲P��`QT�����[UȚ¶�Q�zRAR	B_���x�b�F��7��B��0heΉq��j� @gZ\PR��?���a�C�+����҂��"�����F��`����<"
�O�AM�V�\�vҧ�(m,�Y�w��0����_��3��F5ʪ(8 �p�P�U+���â:G���m!.9/w9//��BׅW��>��o�1��P���^�|�X67F[�p��D����<;��h'UMeɲ2f�#��A:^_ǵ�JH�`y��܅�?1��aL >x�-�B�Ev��j1��f`'N��`��������H�݁���=8���Q⒓d�C������&/hdk�^밻�d���7����������D�Q[X����)F�P!\�S�۔�`N�C����	���&�����n�IQ��˯��:n�T�(���}uY�T�"�9��D�P�;���b1��ᑙ���%��Ғ�;r^�5�9.`*��|<�i�Q�b���)k��ٷ�� ���l}��I�0Y���`묅�n}Ma�x�i\�Y��Tu�g�ل̚b��,��9�͜sp��%��Q����ۋ�)�7�z'�i��~����B[�`Sv�9�B����R���*)~-�����h��I����Y�8����_����@K�����ep[;5̯���,���'��ӳ���g\�W�Aԓ���d��++Hy��!�ِ�	Z(�T���њ�UˬM���Q� ^  ��²�/��N͏��2�4�b0�1ɓICx]�+p�k�n��pߒRA���w]:俟��V�s�ŒJj���f$KWM}踈����}j�0�<����9~,Ri^�W0HՍ\6o�e2�2$͚�Ԋ<
�ϡa��=��"u,G��&0����%���wҿ�=� ��(iBC����"�l��p�B~њ'�euң<�KJ����c8��*��vO�
W�Xt!J��s�<l�+�x�%��s_�+RYT��B O�̎�`fzOKS|[�,���ug�lY?w2S�ʂ�����I0���]x^�K��^{�;�@�ߩh�ߵ[i�n�r���@|YW�hY5Q�m5�+C�6�c�>
�a�U`�����;�:ŢH�Ro�,���_�����X��1��?��|]eB+�qS�p�"U�Ŭ~�����{�lh#�I^�9� xancf@R$('�(Kh�O7��vMx��v2YWQ�k������L^�h��tL�n6�TW�q�t����K�;��
��D���h����Y��T���s��Y3~pw����?���V��X8{�s0��Nj��H!��#O�`��l�nkf�@s����	�	m�Q�S�J���
�Gx����Bs

Ț���?Ry�7Jp�y�2�oO��J�*�q��r��a�1��\a����	����t�<�cKp޲�����B�	�����q��!ᥒ��OX���nG\�\[�;O`W��G*��tO=�,�i��E�a�����4�E�`�pҩJ�0�ZO�Cν�	�ܥN�T&1����%@ʱ�f�ꢄI�N�wf-=�e����f�Cc��U�R�+z�m����-�ȸ*^v垎S��QV>��1�{��Lc$`�Яva��ga璪�dM%�dTE��B/e=4t�Gaί�̨�W�\���:�K�3�W��n��� �G*"�����K��)�cHs g��S�$���2�x�ҾQ[�|u�9�h򳄶 �h��?��NV�2w'GIDh���$<�[�;M�{7"��?J%�^�8��F$�7`��Di!��?���n���A|K���k|2i^X�����wC�sܞ�J�Q����Oȏ�	�%����^r�đ�^hS�J@�!AB[�YA��蝴����}������a��D��WX+���79Y�=�<E�wA��џ��IfF��t�+�K� �BȐJ�0�]���J��T[�_��~v�~_���6����p�<�M��Ҍq�"�$g�ou�ϔ�T^����tr+�$]&m��^U�NYI�O�۟+6����L��^��Ǥ���kx�?�������b��IKcfY3�_��&�eSE(O�D>f���L�ű<y:��ET1�Ҋ��g )~�Ф��%��%�Y�B���H���W�6^���!���>?������J㊒겄*+}�����#a��b�LY,MwktQ��,�5���yV���qa�H�y�"ѿp4p���V��W@�o���k��x(�mx�U��E'"�4DW�s��q.X4�n>�m����Ň�d���k�����b�z��&k�K#�a2�4�������\-j��i����8+��g;�w��4�'�0�/��Vt����U��X��Un�iA:�'8�
�i;mE�:5�:�̴��t��1ʜ��1A��>��b�!���dY[p�:XL�+E@*J~�Up��戼
��A+������V0L��#�O�ڗT��Ւ�N�'���Z�X�<q��6����%5�i��3�6�\��K�k��M4RY'[I��YH9u�ɂL�Q�͜ʉ/�íhR���Կ#�E$A:
]͇fM<�J�sXG��k
�˪*4i��:�w8���.�ݪ��S��"�%�����o�|��)?�Bl`N���hJJ�	q]v�wBa����j�:�ƨ�w�n��s;���t���Aﻎ��Ǔq��^���)��.���
}�ItƨȻ��uI-:jJ���JI�S�_F�b�Su����+?����$����hU�\F�_�����IL)�,�b��f�M�B7 �P7����K������r�UT �U):���!e�Bd2L�������˟����<u�      �   ,   x���  ��w;�w� ���s�L�A�Xn�IZ��c�^�o�i�      �   $  x�u�K�ICѱj1}����:E��|>�	)E0�J\K�|����9�����~�k�@;�����;��M�;8'�
���݁�����`/���;�
4,�L ���H�&���9��߽�v�	�K��K�{���tL��&�g�y_�Hܿ�®�	�������֘hW�{@��	�v5�+�����gn/����0�?8{���Y���c8�{� ��L�'��F��	`O�'?���s��s����`��|'�#�{�+k4m�z&�^5��������!��H.M�'��	��w^J��}N��q��9�c���[쳺k���۟��o��t��az��	�3�wX��`��0�3���9&ا�h�}�o�8q�s��3��N���\�v�	�����[S+�	`��
���'ػz�	��9�� ��8&٧�z�=��	� G&����&��\��Z�㞄�ܓ�g�s ��I�:�&هV�oc�V�nl�:�I`Ҭ����QY����j��1	�N��E^Y���+W7Vy�.����kcgW�Q�l@e�Ƣ�|�M_yC�5\yX��c	P9NaEW�&Be�BlT�B	T�$����^a��
��B@a�W&4GEn�ޯHSX��	[�b4a�W*,슠��� U���	˼bW�1* ��Ba;W<(,�
!��]њ��+P���5!5*��B9!*,�q�SB
T�'�@��BT�%��
τ�P(����R��MX��
IPA��l
���1a�W�'�DE�B�T0(C�D�n�8Q��a����a�߉u]��9���o!�I��)q�y��$�r��]�}��M����	���{;�v*�C�+vH�'�C*V@`'tHǂ��K��A���cN�):+ j��7�):~���Ac�v�d�펞�>�x�h��{�>�hШ�ή���ب�����\m����FSt3ʡ3�Q�}й�(��
Fmti�����ܑ���;�5*��D�6:�3v}GiFmttkTM�����)`�:4ʡs5�v�'Ʈ�@̨��G��蔏��ۄ��7��Ν����;�й�����6j����y�h���]�q�Q���cX�:3v}GvFt6
�cK�:�3v}ǉ�o��7c�wtk4EG\��c>c�wtk�CG�F9t�h�?5�V'�/��ߓ��1��W�x���@i��!N4��c1�:�2wp4h��-�>����D�hJǣFSttkJGvƮ��胎aͦ��̍�}Б���Y���1�ɽ[�,�X��ގ�N����dmģ'�"V>Y�݉B������� eN��      �   �   x�34���,.9��(3��И3(5'3=���|.CN������4��)�kJir���@)��k�3���f����y�9\���E%�饇WsZp�&���^XVh��yxU^rfb1���cQI�B��H�=... �n2      �   x   x���	�@�г�����{I�uDc��ZF�Xj=G� \A����Z�7�?[��`��?8���n���ן�^��^������`}�>G���j40���p�����\}��h^(!      �   �   x�-α�@��U�!�(�ߋc�_�����M�k�R�{Գ�<O�%6�?�F4�D�-<�oq͠4�r��A��0(aP �A9�r� �A��0hà�v0��0hà�v0��0��a� .q�y��MJ�      �      x�UV�r)>���	T��fǎ�NɎ�J%��J13H��^`���&��nUN{۫^l����M�}4tS�أ��q���O�-1W�p^�{�����Z˿D[[�蘆���_f�Q�d��� �����F(=�L3�M H����-�H�k�_���鬫`� �"��U�>H�v�)��u��[%K--�m�>&Hu����ݑ��w�7���f�ު#�1R�+#�(��ŴM�~��3g7�Q��V���wBY����!TeT.�9� �	)zQ%$@$$ʂs��'gsf�`������ӏ�V�?I獅\�V�:	1i��DST�k��M�y�+vc������(�W\f�C�n1� �#v+��|�I-�xZ�c�QG¨]0t��*�NG5/�{�x�]+��� i%9l� d��7v�?�ӿV%"	 0�^Fӿ5�	{�<h��+���/;�AC$�]�[0Σ���^���uW�V�]dM���6�S��؃��ʫV�Uc�(ݟ�򂩠&Bx�������s�p0�i�xkE�k��F���Ki=Shǀy�ߢ���`D+N� �Mm,���V�ڳWP@̉Rx�E4ɳ�D UI^Cp��G()Oz���g��*׏h�>tP,�Ik�7P�����Fi�	(r.e-F����~�'i9<gȕA�&z�A�͚mR���ǯ���C�<#c���h�{��[^.����"�hIVd����뽘���?H�_��T���߄=2��"�	�.��g���u����7��.�ܵn��r8��}����b����54l$�rg���WEc��&5Y�����$�-`����l���U	�IAb�<�;	�pDR۱[�|��Ԓ=�~԰����_U�І?c�o���q$��MA���r:-r�{�an�����%M�>��^�E��������=�3J6#@ �
�(�,�e���	��`�s 0Z��2�����B�2�D��ب%ac��55�Ί]o'���D)k��5���0e�?iwP���U{�A$�ev�h�M1��(���Ay���J���U�A��$pC&�=�٦��7����r���u
�݂�n��W��	j�K5*ϐ���效�9ƒ}�p;e����>ju�9;��<!���ɢ��{���G(l��!݀�q��wݛ�A�E��+[��)�Q׭MA��<�OT��hV|�<����8��vU��G��8�ьٽ(᫩Ӌ�{��fn`����j5a�c���x��E6Q��8lr�*�Z�D�����|�QH$a������`0�7�&2      �   �  x�5�Ar!D�p�������i�*��	�s'6e�́5�2�Ӽ��X4�T���-{	�z9%��l.	�T�}�n����{��e:']`���]�M8�]�C�������t��.�t�7]���v�K2�>t��A��O��Nx����.pv�7]KבZ�ZCr�lt������]��k���.��yo.���a�������`8ٳu{���Z�q-xro��prmj�M�ἵ����|�]<C����/����9Π�=���p�yG����^��^�>S1|����A^gi�w'�*�Y��~0\�s���w7J�`'��g��N�Q?8�o��I�������`��0������	;�3v�O�!��C=i��7�����c����=7=7L5l��`,�a��g��m�>�����G��n��������S��#�      �   ]   x�ɱ�0��(�� )�������\���55vlp‫����'�����=�kb��Pǁ�N��w�?��-���Ol4��%ZQ�~ ��x     