[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ToxiquOi_P9_Mediscreen&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=ToxiquOi_P9_Mediscreen)

# Mediscreen-services

## Description du repository
Ce repository Git contient l'ensemble des applications produite dans le cadre du 9ème projet OCR.

Ce repository contient les applications suivante:
- userdb: projet du sprint 1 gérant les données des patients.
- doctordb: projet du sprint 2 gérant l'historique médical des patients.
- analytics: projet du sprint 3 permettant de générer des rapports via l'ensemble des informations des patients.
- mediscreen-ui: projet ayant évoluer dans l'ensemble des sprints, il contient l'IHM utilisateur.

## Technologies
- Java 11
- Vaadin
- Spring boot
- MySQL 8.0.27
- MongoDB latest
- Docker
- Github pipeline

## Compilation et démarrage des projets
Ce projet utilise gradle comme moteur de gestion de dépendance et de build.
Une partie des tests unitaires des applications nécessite des accès aux BDD.

L'ensemble des commandes suivantes sont à lancer depuis la racine du projet.

Démarrer les BDD:
- docker-compose -f docker/only-db-test/compose.yml up --force-recreate

Lancer la compilation:
- ./gradlew clean build

Détruire les images et container précedement créer:
- docker container rm $(docker container ls)
- docker image rm $(docker image ls)

Lancer les applications:
- docker-compose -f docker/compose.yml up --build --force-recreate
