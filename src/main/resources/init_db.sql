CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8;
CREATE TABLE `internet_shop`.`products` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `price` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`product_id`));

CREATE TABLE `internet_shop`.`users` (
  `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(256) NOT NULL,
  `name` VARCHAR(256) NULL,
  `password` VARCHAR(256) NOT NULL,
  `salt` VARBINARY(500) NOT NULL,
  PRIMARY KEY (`user_id`));

CREATE TABLE `internet_shop`.`shopping_carts` (
  `cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NULL,
  PRIMARY KEY (`cart_id`),
  INDEX `shopping_carts_users_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `shopping_carts_users_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet_shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `internet_shop`.`shopping_carts_products` (
  `cart_id` BIGINT(11) NULL,
  `product_id` BIGINT(11) NULL,
  INDEX `cart_products_cart_fk_idx` (`cart_id` ASC) VISIBLE,
  INDEX `cart_products_products_fk_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `cart_products_cart_fk`
    FOREIGN KEY (`cart_id`)
    REFERENCES `internet_shop`.`shopping_carts` (`cart_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `cart_products_products_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `internet_shop`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `internet_shop`.`orders` (
  `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NULL,
  PRIMARY KEY (`order_id`),
  INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `orders_users_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet_shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `internet_shop`.`orders_products` (
  `order_id` BIGINT(11) NULL,
  `product_id` BIGINT(11) NULL,
  INDEX `orders_products_orders_fk_idx` (`order_id` ASC) VISIBLE,
  INDEX `orders_products_products_fk_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `orders_products_orders_fk`
    FOREIGN KEY (`order_id`)
    REFERENCES `internet_shop`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `orders_products_products_fk`
    FOREIGN KEY (`product_id`)
    REFERENCES `internet_shop`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `internet_shop`.`roles` (
  `role_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE);

INSERT INTO roles (role_name) VALUES ('ADMIN');
INSERT INTO roles (role_name) VALUES ('USER');

CREATE TABLE `internet_shop`.`users_roles` (
  `user_id` BIGINT(11) NULL,
  `role_id` BIGINT(11) NULL,
  INDEX `users_roles_users_fk_idx` (`user_id` ASC) VISIBLE,
  INDEX `users_roles_roles_fk_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `users_roles_users_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet_shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `users_roles_roles_fk`
    FOREIGN KEY (`role_id`)
    REFERENCES `internet_shop`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
