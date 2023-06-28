DELETE FROM public.order_registration;
DELETE FROM public."order";
DELETE FROM public.product;
DELETE FROM public."user";

INSERT INTO public."user" (id, "name") VALUES(1, 'Sammie Baumbach');
INSERT INTO public."user" (id, "name") VALUES(2, 'Augustus Aufderhar');
INSERT INTO public."user" (id, "name") VALUES(3, 'Carolann Walker');
INSERT INTO public."user" (id, "name") VALUES(4, 'Miss Colton Kris');

INSERT INTO public.product (id) VALUES(1);
INSERT INTO public.product (id) VALUES(2);
INSERT INTO public.product (id) VALUES(3);
INSERT INTO public.product (id) VALUES(4);

INSERT INTO public."order" (id, user_id, created_at) VALUES(1, 1, '2021-10-28');
INSERT INTO public."order" (id, user_id, created_at) VALUES(2, 2, '2021-09-10');
INSERT INTO public."order" (id, user_id, created_at) VALUES(3, 3, '2021-06-21');
INSERT INTO public."order" (id, user_id, created_at) VALUES(4, 4, '2021-03-18');

INSERT INTO public.order_registration (id, order_id, product_id, value)
VALUES('36f18d5f-d48c-40af-aff0-f9a2a1f0b96a'::uuid, 1, 1, 725.86);
INSERT INTO public.order_registration (id, order_id, product_id, value)
VALUES('49fb8eec-86b9-4852-8f6a-997772d03270'::uuid, 1, 2, 1465.30);
INSERT INTO public.order_registration (id, order_id, product_id, value)
VALUES('ae434dc9-1cb5-4187-a0bf-b9786ccda198'::uuid, 2, 3, 973.27);
INSERT INTO public.order_registration (id, order_id, product_id, value)
VALUES('ac75b502-d5d4-44d4-b414-ccccc76981da'::uuid, 2, 4, 1320.49);
INSERT INTO public.order_registration (id, order_id, product_id, value)
VALUES('d9acf60b-1891-49d9-ab69-ff5baff11bf4'::uuid, 2, 1, 971.68);
INSERT INTO public.order_registration (id, order_id, product_id, value)
VALUES('c3341f6d-c6bc-47c8-b6da-364b7ffc446e'::uuid, 3, 2, 179.56);
INSERT INTO public.order_registration (id, order_id, product_id, value)
VALUES('cebc39bc-44df-426d-9f3c-94925d2a69ed'::uuid, 3, 3, 419.11);
INSERT INTO public.order_registration (id, order_id, product_id, value)
VALUES('35b3f824-9f42-4459-a9c8-dca5eb9106a9'::uuid, 3, 4, 219.97);
INSERT INTO public.order_registration (id, order_id, product_id, value)
VALUES('7a9b31f6-87ba-44cf-b6bb-cb62b34ed09e'::uuid, 3, 1, 1862.16);
INSERT INTO public.order_registration (id, order_id, product_id, value)
VALUES('f89aee5a-76d7-440e-a041-47b2608ea2fa'::uuid, 3, 2, 343.37);
INSERT INTO public.order_registration (id, order_id, product_id, value)
VALUES('2485f033-8e51-4298-8b3e-5a30cf22066f'::uuid, 4, 3, 93.26);
