DELETE FROM templateDataItem
DELETE FROM templateData
DELETE FROM templateGroup
DELETE FROM template
DELETE FROM templateDataType
INSERT INTO template (id, name, orderItem, id_language, serverSync, deleted) VALUES (1, 'General', 1, 1, 0, 0)
INSERT INTO templateDataType (id,type,id_language) VALUES (1,'Short text',1)
INSERT INTO templateDataType (id,type,id_language) VALUES (2,'Medium text',1)
INSERT INTO templateDataType (id,type,id_language) VALUES (3,'Texto Largo',1)
INSERT INTO templateDataType (id,type,id_language) VALUES (4,'Multiline Text Large',1)
INSERT INTO templateDataType (id,type,id_language) VALUES (5,'Date',1)
INSERT INTO templateDataType (id,type,id_language) VALUES (6,'Date and Time',1)
INSERT INTO templateDataType (id,type,id_language) VALUES (7,'Yes and no',1)
INSERT INTO templateDataType (id,type,id_language) VALUES (8,'Selection list options',1)
INSERT INTO templateDataType (id,type,id_language) VALUES (13,'List',1)
INSERT INTO templateGroup (id,name,orderItem,id_template,multiple) VALUES(1,'IDENTITY',1,1,0)
INSERT INTO templateGroup (id,name,orderItem,id_template,multiple) VALUES(2,'EMERGENCY CONTACT',2,1,1)
INSERT INTO templateGroup (id,name,orderItem,id_template,multiple) VALUES(3,'IMPORTANT MEDICAL INFORMATION',3,1,0)
INSERT INTO templateGroup (id,name,orderItem,id_template,multiple) VALUES(4,'DRUGS THAT SHOULD NOT TAKE',4,1,0)
INSERT INTO templateGroup (id,name,orderItem,id_template,multiple) VALUES(5,'DRUGS CURRENTLY TAKE',5,1,0)
INSERT INTO templateGroup (id,name,orderItem,id_template,multiple) VALUES(6,'IMPORTANT ADDITIONAL INFORMATION',6,1,0)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (4,4,'Address',1,0,4)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (5,2,'Home phone',1,0,5)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (6,2,'Phone office',1,0,6)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (7,2,'Mobile phone',1,0,7)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (8,1,'Height (m:cm)',1,0,8)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (9,1,'Weight',1,0,9)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (10,3,'Social Security Code',1,1,10)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (11,8,'Blood type',1,0,11)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (3,11,'O-',1)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (4,11,'O+',2)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (5,11,'A-',3)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (6,11,'A+',4)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (7,11,'B-',5)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (8,11,'B+',6)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (9,11,'AB-',7)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (10,11,'AB+',8)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (13,8,'Civil status',1,0,13)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (11,13,'married',1)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (12,13,'Single',2)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (13,13,'Free Union',3)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (14,13,'Widow(er)',4)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (14,3,'Occupation',1,0,14)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (15,3,'e-mail',1,0,15)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (16,3,'Name',2,0,16)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (18,4,'Address',2,0,18)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (19,3,'Home phone',2,0,19)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (20,3,'Phone office',2,0,20)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (21,3,'Mobile phone',2,0,21)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (22,8,'Relationship',2,0,22)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (15,22,'Wife or Husband',1)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (16,22,'Daughter or Son',2)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (17,22,'Father',3)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (18,22,'Mother',4)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (19,22,'Sister or Brother',5)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (20,22,'Grandmother or Grandfather',6)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (21,22,'Aunt or uncle',7)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (22,22,'Niece or nephew',8)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (23,22,'Sister in law or Brother in law',9)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (24,22,'Daughter in law or Son in law',10)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (26,22,'Mother in law or Father in law',12)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (27,22,'Neighbour',13)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (28,22,'Friend',14)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (29,22,'Other',15)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (23,7,'Heart disease',3,0,23)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (24,7,'High blood pressure',3,0,24)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (25,7,'visual impairment',3,0,25)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (26,7,'Diabetes',3,0,26)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (27,7,'Low blood pressure',3,0,27)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (28,7,'Inability to speak',3,0,28)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (29,7,'Epilepsy / Seizures',3,0,29)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (30,7,'hearing impairment',3,0,30)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (31,4,'Others',3,0,31)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (32,7,'Contact lenses?',3,0,32)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (33,7,'have a pacemaker?',3,0,33)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (34,7,'have a will?',3,0,34)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (35,7,'have dentures?',3,0,35)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (30,35,'Do not have',1)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (31,35,'Top',2)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (32,35,'Lower',3)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (33,35,'Top and Lower',4)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (36,7,'¿Injection pneumonia?',3,0,36)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (37,5,'Date of application',3,0,37)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (38,13,'Name of medication (allergy, side effects or reaction)',4,0,38)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (39,13,'Including prescription and non-prescription',5,0,39)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (40,4,'Other information not included in previous sections',6,0,40)
INSERT INTO template (id, name, orderItem, id_language, serverSync, deleted) VALUES (2, 'General', 2, 2, 0, 0)
INSERT INTO templateDataType (id,type,id_language) VALUES (101,'Texto Corto',2)
INSERT INTO templateDataType (id,type,id_language) VALUES (102,'Texto Medio',2)
INSERT INTO templateDataType (id,type,id_language) VALUES (103,'Texto Largo',2)
INSERT INTO templateDataType (id,type,id_language) VALUES (104,'Texto Grande Multilinea',2)
INSERT INTO templateDataType (id,type,id_language) VALUES (105,'Fecha',2)
INSERT INTO templateDataType (id,type,id_language) VALUES (106,'Fecha y Hora',2)
INSERT INTO templateDataType (id,type,id_language) VALUES (107,'Si o No',2)
INSERT INTO templateDataType (id,type,id_language) VALUES (108,'Lista Selección de Opciones',2)
INSERT INTO templateDataType (id,type,id_language) VALUES (109,'Listado',2)
INSERT INTO templateGroup (id,name,orderItem,id_template,multiple) VALUES(101,'IDENTIDAD',1,2,0)
INSERT INTO templateGroup (id,name,orderItem,id_template,multiple) VALUES(102,'CONTACTO EN EMERGENCIA',2,2,1)
INSERT INTO templateGroup (id,name,orderItem,id_template,multiple) VALUES(103,'INFORMACION MEDICA IMPORTANTE',3,2,0)
INSERT INTO templateGroup (id,name,orderItem,id_template,multiple) VALUES(104,'MEDICAMENTOS QUE NO PUEDE TOMAR',4,2,0)
INSERT INTO templateGroup (id,name,orderItem,id_template,multiple) VALUES(105,'MEDICAMENTOS QUE TOMO ACTUALMENTE',5,2,0)
INSERT INTO templateGroup (id,name,orderItem,id_template,multiple) VALUES(106,'INFORMACION IMPORTANTE ADICIONAL',6,2,0)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (104,104,'Dirección',101,0,4)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (105,102,'Teléfono casa',101,0,5)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (106,102,'Teléfono oficina',101,0,6)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (107,102,'Teléfono móvil',101,0,7)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (108,101,'Altura (m:cm)',101,0,8)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (109,101,'Cuanto pesa',101,0,9)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (110,103,'Seguridad Social',101,1,10)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (111,108,'Tipo de sangre',101,0,11)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (103,111,'O-',1)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (104,111,'O+',2)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (105,111,'A-',3)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (106,111,'A+',4)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (107,111,'B-',5)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (108,111,'B+',6)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (109,111,'AB-',7)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (110,111,'AB+',8)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (113,108,'Estado civil',101,0,13)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (111,113,'Casada(o)',1)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (112,113,'Soltera(o)',2)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (113,113,'Unión libre',3)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (114,113,'Viuda(o)',4)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (114,103,'Ocupación',101,0,14)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (115,103,'Correo electrónico',101,0,15)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (116,103,'Nombre Completo',102,0,16)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (118,104,'Dirección',102,0,18)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (119,103,'Teléfono casa',102,0,19)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (120,103,'Teléfono oficina',102,0,20)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (121,103,'Teléfono móvil',102,0,21)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (122,108,'Parentesco',102,0,22)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (115,122,'Conyuge',1)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (116,122,'Hija o Hijo',2)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (117,122,'Padre',3)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (118,122,'Madre',4)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (119,122,'Hermana o Hermano',5)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (120,122,'Abuela o Abuelo',6)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (121,122,'Tia o Tio',7)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (122,122,'Sobrina o sobrino',8)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (123,122,'Cuñada o Cuñado',9)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (124,122,'Yerna o Yerno',10)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (125,122,'Concuña o Concuño',11)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (126,122,'Suegra o Suegro',12)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (127,122,'Vecina o Vecino',13)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (128,122,'Amiga o Amigo',14)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (129,122,'Otro',15)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (123,107,'Enfermedad cardíaca',103,0,23)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (124,107,'Presión sanguínea alta',103,0,24)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (125,107,'Incapacidad visual',103,0,25)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (126,107,'Diabetes',103,0,26)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (127,107,'Presión sanguínea baja',103,0,27)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (128,107,'Incapacidad de hablar',103,0,28)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (129,107,'Epilepsia/Convulsiones',103,0,29)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (130,107,'Incapacidad auditiva',103,0,30)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (131,104,'Otros especificar',103,0,31)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (132,107,'¿Lentes de contacto?',103,0,32)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (133,107,'¿Tiene marcapasos?',103,0,33)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (134,107,'¿Tiene testamento?',103,0,34)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (135,107,'¿Tiene dentaduras?',103,0,35)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (130,135,'No tiene',1)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (131,135,'Superiores',2)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (132,135,'Inferiores',3)
INSERT INTO templateDataItem (id,id_templateData,itemName, orderItem) VALUES (133,135,'Superiores e inferiores',4)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (136,107,'¿Inyección de neumonía?',103,0,36)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (137,105,'Fecha de aplicación',103,0,37)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (138,109,'Nombre del medicamento (alergia, efectos secundarios o reacción)',104,0,38)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (139,109,'Incluyendo recetados y no recetados',105,0,39)
INSERT INTO templateData (id,id_templateDataType,title,id_templateGroup, required, orderItem) VALUES (140,104,'Otra información no incluida en apartados anteriores',106,0,40)
