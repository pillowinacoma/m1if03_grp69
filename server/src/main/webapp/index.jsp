<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Présence UCBL</title>
  <link rel="stylesheet" type="text/css" href="static/presence.css">
</head>
<body>
<jsp:include page="composants/header.jsp"/>

<main id="contenu" class="wrapper">
  <aside class="menu"></aside>
  <article class="contenu">
    <h1>Bienvenue sur Présence UCBL</h1>
    <form method="post" action="Init">
      <p>
        <label>
          Entrez votre login :
          <input type="text" name="login" autofocus>
        </label>
      </p>
      <p>
        <label>
          Entrez votre nom :
          <input type="text" name="nom"> (facultatif)
        </label>
      </p>
      <p>
        <label>
          Êtes-vous administrateur ?
          <input type="checkbox" name="admin">
        </label>
      </p>
      <p>
        <input type="submit" value="Connexion">
      </p>
    </form>
  </article>
</main>

<jsp:include page="composants/footer.html"/>
</body>
</html>