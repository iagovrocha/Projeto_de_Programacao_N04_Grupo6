CREATE DATABASE  IF NOT EXISTS `gerenciador_evento_api`;
USE `gerenciador_evento_api`;

DROP TABLE IF EXISTS `eventos`;
CREATE TABLE `eventos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `local` varchar(45) NOT NULL,
  `data` varchar(45) NOT NULL,
  `preco` decimal(10,2) NOT NULL DEFAULT '0.00',
  `id_user` bigint NOT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `logsnotificacao`;
CREATE TABLE `logsnotificacao` (
  `id_log_notificacao` bigint NOT NULL AUTO_INCREMENT,
  `id_notificacao` bigint NOT NULL,
  `method` varchar(45) NOT NULL,
  `func_name` varchar(45) NOT NULL,
  `id_user` bigint DEFAULT NULL,
  PRIMARY KEY (`id_log_notificacao`)
);

DROP TABLE IF EXISTS `notificacao`;
CREATE TABLE `notificacao` (
  `id_notificacao` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint DEFAULT NULL,
  `titulo` varchar(255) NOT NULL,
  `mensagem` varchar(255) NOT NULL,
  `status_envio` tinyint NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `data_horario_envio` datetime DEFAULT NULL,
  `id_tag` bigint DEFAULT NULL,
  PRIMARY KEY (`id_notificacao`)
);

DROP TABLE IF EXISTS `notificacaousuario`;
CREATE TABLE `notificacaousuario` (
  `id_notificacao` bigint NOT NULL,
  `id_user` bigint NOT NULL,
  `lida` tinyint NOT NULL,
  PRIMARY KEY (`id_notificacao`,`id_user`)
);

DROP TABLE IF EXISTS `pagamentos`;
CREATE TABLE `pagamentos` (
  `id_pagamento` bigint NOT NULL AUTO_INCREMENT,
  `id_usuario` bigint NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `criado_em` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_pagamento` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint NOT NULL,
  PRIMARY KEY (`id_pagamento`)
);

DROP TABLE IF EXISTS `preferencia`;
CREATE TABLE `preferencia` (
  `id_preferencia` bigint NOT NULL AUTO_INCREMENT,
  `id_user` bigint NOT NULL,
  `tipo` varchar(45) NOT NULL,
  PRIMARY KEY (`id_preferencia`)
);


DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags` (
  `id_tag` bigint NOT NULL AUTO_INCREMENT,
  `nome_tag` varchar(100) NOT NULL,
  `cor_tag` varchar(45) NOT NULL,
  `ativo` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_tag`)
);

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id_user` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `senha` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id_user`)
);

DROP TABLE IF EXISTS `usuario_evento`;
CREATE TABLE `usuario_evento` (
  `evento_id` bigint NOT NULL,
  `id_user` bigint NOT NULL,
  PRIMARY KEY (`evento_id`,`id_user`),
  KEY `usuario_usuario_evento_idx` (`id_user`),
  CONSTRAINT `evento_usuario_evento` FOREIGN KEY (`evento_id`) REFERENCES `eventos` (`id`),
  CONSTRAINT `usuario_usuario_evento` FOREIGN KEY (`id_user`) REFERENCES `usuario` (`id_user`)
);
