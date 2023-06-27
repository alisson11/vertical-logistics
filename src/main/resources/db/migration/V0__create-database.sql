CREATE TABLE IF NOT EXISTS public.user
(
	id				bigint							NOT NULL,
    name			varchar(50)						NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.product
(
    id          	bigint							NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.order
(
    id				bigint  						NOT NULL,
    user_id			bigint 							NOT NULL,
    created_at		DATE							NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.user
);

CREATE TABLE IF NOT EXISTS public.order_registration
(
	id				uuid							NOT NULL,
	order_id		bigint							NOT NULL,
    product_id		bigint          				NOT NULL,
    value			numeric(12,2)					NOT NULL,
    CONSTRAINT pk_order_registration PRIMARY KEY (id),
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES public.order,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES public.product,
    UNIQUE(order_id, product_id, value)
);