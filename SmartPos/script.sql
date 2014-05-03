CREATE TABLE sp_products(
	id integer primary key autoincrement,
	code text,
	name text,
	description text,
	unit text,
	price double);

CREATE TABLE sp_users(
	id integer primary key autoincrement,
	name text,
	user text,
	pwd text);

CREATE TABLE sp_sales(
	id integer primary key autoincrement,
	folio text,
	subtotal double,
	iva double,
	total double,
	issued date,
	mode text,
	status text);
	
CREATE TABLE sp_det_sales(
	id integer primary key autoincrement,
	folio text,
	code text,
	quantity double);

CREATE TABLE sp_stock(
	id integer primary key autoincrement,
	code text,
	quantity double);
