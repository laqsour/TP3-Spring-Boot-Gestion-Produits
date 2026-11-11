# Gestion des produits — Spring Boot / JEE

Application pédagogique complète : Spring Web MVC, Spring Data JPA, Hibernate, H2, MySQL (profil optionnel), Thymeleaf, Bootstrap, Validation et Spring Security.

## 1. Prérequis Windows
- JDK 21 installé.
- IntelliJ IDEA (ou VS Code avec extensions Java) ou terminal PowerShell.
- Maven 3.9+ installé, ou Maven intégré à IntelliJ.
- Internet pour télécharger les dépendances Maven et charger Bootstrap CDN.

Vérifier dans PowerShell :
```powershell
java -version
mvn -version
```
La commande Java doit afficher la version 21.

## 2. Ouvrir et lancer dans IntelliJ
1. Télécharger et extraire `gestion-produits-spring.zip`.
2. Ouvrir IntelliJ → **File → Open** → sélectionner le dossier `gestion-produits-spring` (celui qui contient `pom.xml`).
3. Si IntelliJ demande **Trust Project**, accepter. Choisir le JDK 21 dans Project SDK.
4. Attendre l'import Maven et le téléchargement des dépendances.
5. Ouvrir `src/main/java/ma/oussama/gestionproduits/GestionProduitsApplication.java`.
6. Cliquer sur le triangle vert à côté de `main`, puis **Run**.
7. Attendre le message `Started GestionProduitsApplication`.
8. Ouvrir `http://localhost:8080` dans Chrome.

## 3. Lancer avec PowerShell
Dans PowerShell, se placer dans le dossier extrait (adapter le chemin) :
```powershell
cd "C:\Users\ASUS\Downloads\gestion-produits-spring"
mvn spring-boot:run
```
Arrêter avec `Ctrl + C`.

## 4. Connexion
- URL : `http://localhost:8080`
- Utilisateur : `admin`
- Mot de passe : `admin123`

Changez ce mot de passe avant toute utilisation réelle. Le compte est uniquement destiné à la démonstration locale.

## 5. Fonctionnalités
- Liste des produits et données de démonstration.
- Ajout avec validation (nom obligatoire, prix positif, quantité non négative).
- Modification et suppression.
- Recherche par nom ou description.
- Connexion/déconnexion Spring Security.
- Interface Thymeleaf + Bootstrap.
- Tests de la couche repository avec H2.

## 6. Tester la couche DAO
Dans IntelliJ, clic droit sur `ProductRepositoryTest` → **Run**.
Ou dans PowerShell :
```powershell
mvn test
```
Les tests vérifient l'enregistrement, la lecture et la recherche.

## 7. Base H2
Le projet démarre par défaut avec H2 en mémoire. La console H2 est activée à `/h2-console`, mais l'accès est protégé par Spring Security dans la configuration finale. Pour un TP, on peut temporairement autoriser `/h2-console/**` et désactiver CSRF/les en-têtes frame dans `SecurityConfig`, puis remettre la sécurité normale après le test. Ne laissez pas ces exceptions en production.

## 8. Passer à MySQL (optionnel)
1. Installer MySQL Server et démarrer le service.
2. Créer la base `gestion_produits` (ou laisser `createDatabaseIfNotExist=true` si le compte a les droits).
3. Dans PowerShell, définir les variables :
```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/gestion_produits?createDatabaseIfNotExist=true"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="VOTRE_MOT_DE_PASSE"
```
4. Dans IntelliJ, ajouter `--spring.profiles.active=mysql` dans Run → Edit Configurations → Program arguments.
   Ou lancer :
```powershell
mvn spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=mysql"
```
Ne publiez jamais vos identifiants de base de données.

## 9. Comprendre l'architecture
- `entity/Product.java` : modèle JPA + règles de validation.
- `repository/ProductRepository.java` : DAO Spring Data, sans implémentation manuelle.
- `controller/ProductController.java` : routes MVC et traitement des formulaires.
- `config/SecurityConfig.java` : authentification et autorisations.
- `templates/` : pages HTML Thymeleaf.
- `application.properties` : configuration H2 par défaut.

## 10. Parcours conseillé pour présenter le TP
1. Montrer l'entité Product et expliquer `@Entity`, `@Id`, `@GeneratedValue`.
2. Montrer `ProductRepository extends JpaRepository`.
3. Exécuter `mvn test` et expliquer les tests DAO.
4. Montrer le contrôleur et les routes GET/POST.
5. Ajouter un produit valide puis un produit invalide pour montrer la validation.
6. Modifier, rechercher et supprimer un produit.
7. Se déconnecter puis vérifier que `/products` redirige vers la connexion.
8. Expliquer que Spring Security protège toutes les routes sauf `/login` et les ressources statiques.

## 11. Problèmes fréquents
- **Port 8080 déjà utilisé** : modifier `server.port=8081`, puis ouvrir `http://localhost:8081`.
- **Java version incorrecte** : vérifier `java -version` et le Project SDK IntelliJ = 21.
- **Maven ne télécharge pas** : vérifier Internet, puis Maven → Reload Project.
- **Lombok / erreurs de getters** : activer l'annotation processing dans IntelliJ (Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Enable annotation processing), puis recharger Maven.
- **Page inaccessible** : vérifier que la console affiche `Started GestionProduitsApplication` et que le serveur tourne.

Projet conçu pour l'apprentissage local, pas pour être déployé tel quel en production.
