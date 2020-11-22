<%@ page contentType="text/html; charset=UTF-8" %>

<aside class="menu">
<p><strong><em>Menu</em></strong></p>
<br>
<p><strong>Voir les passages</strong></p>
<ul>
<li>
<em>Par utilisateur</em><br>
<form method="get" action="admin">
<label>
                    Login :
<input type="text" name="login" autofocus>
</label>
<input type="hidden" name="contenu" value="passages">
<input type="submit" value="Envoyer">
</form>
</li>
<li>
<em>Par salle</em><br>
<form method="get" action="admin">
<label>
                    Nom :
<input type="text" name="nomSalle" autofocus>
</label>
<input type="hidden" name="contenu" value="passages">
<input type="submit" value="Envoyer">
</form>
</li>
<li>
<em><a href="admin?contenu=passages">Tous</a></em>
</li>
</ul>
<p><strong>Autres ressources</strong></p>
<ul>
<li><em><a href="admin?contenu=salles">Salles</a></em></li>
<li><em><a href="admin?contenu=users">Utilisateurs</a></em></li>
</ul>

<p><a href="presence">Interface classique</a></p>
<p><a href="Deco">Déconnexion</a></p>
</aside>