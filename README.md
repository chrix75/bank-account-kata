# bank-account-kata

## Présentation 

Ce projet présente certaines opérations sur un compte bancaire.
Ces opérations sont: dépot d'argent, retrait d'argent et consultation des opérations d'un compte.

La note du Kata informait qu'on pouvait se baser sur notre expérience personnelle.
J'ai donc décidé de considérer la notion de devises dans les scénarios pour les user stories de retrait et de dépôt.

## Récupération du projet

Obtention du code: ```git clone https://github.com/chrix75/bank-account-kata.git```

Exécution des tests: ```mvn clean test```   
Cette commande exécutera les tests unitaires et les scénarios écrits en langage Gherkin. Ces scénarios sont exécutés par Cucumber 
intégré à JUnit.


## Choix techniques

+ Java 8
+ Le code de production est en pur Java et n'utilise aucune librairie ou framework
+ Le code de test utilise Junit 4 et Cucumber
+ Maven pour le build

## Méthodologie

J'ai commencé par écrire les scénarios en me basant sur le 3 user stories fournis. Cela m'a permis de définir un ensemble de 
spécification par des exemples.
Ces scénarios exécutés par Cucumber sont écrits en anglais et se repose sur la syntaxe Gherkin 
J'ai également défini un glossaire dont les définitions sont en français et fourni la description du personnage principale des 
scénarios.

Une fois les scénarios écrits, j'ai pu passer à la conception et l'implémentation.

## Note

Dans l'en-tête des classes, je donne des informations sur les choix de conception (si besoin).

Pour générer la javadoc avec ces informations, exécutez la commande ```mvn javadoc:javadoc```. Le fichier index.html sera dans le répertoire ```target/site/apidocs/```.


