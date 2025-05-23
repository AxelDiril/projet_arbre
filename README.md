# GUIDE D'INSTALLATION DU PROJET ARBRE

## Prérequis

* Avoir `ant` installé pour la compilation. Pour l'installer, ouvrir un terminal et taper :

```
sudo apt update
sudo apt install ant
```

## Instructions

* Importer le script `create_insert_arbre.sql` dans votre base de données
* Ouvrir un terminal et lancer `Installation_Arbre.jar` avec la commande :
```
java -jar Installation_Arbre.jar
```
* Saisissez le **lien**, le **login** et le **mot de passe** de votre base de données et cliquez sur **Valider**. Cela permettra de compléter automatiquement le fichier `Cl_Connection.java` du dossier `/src` pour que l'application puisse se connecter à la base de données.
* Aller à la racine du projet (là où se trouve `build.xml`) manuellement ou avec `cd Pj_Arbre` dans le terminal
* Pour compiler le projet en exécutable Jar, écrire :
```
ant clean
ant build
```
* L'exécutable est créé dans le dossier `/dist`. Vous pouvez donc lancer l'application avec :
```
java -jar dist/Pj_Arbre.jar
```