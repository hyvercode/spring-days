INSERT INTO public.roles (role_id,name) VALUES
	 ('1','ADMIN');

INSERT INTO public.users (user_id,username,password,name,email,phone_number,device_id,is_active,created_by,created_time,updated_by,updated_time,deleted_time) VALUES
	 ('1','admin','$2a$10$eyBsijn62IsT1hmMgYteQuO/Xsk/i76SS.zaKLEGiuVoFYAGF3y.u','Admin','admin@springdays.com','087885876','1',true,'System','2023-05-31 13:04:14.519458','System','2023-05-31 13:04:14.519458',NULL);

INSERT INTO public.users_roles (user_id,role_id) VALUES
	 ('1','1');
