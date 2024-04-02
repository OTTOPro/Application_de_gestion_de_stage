package crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs

import crosemont.dti.g55.application_gestion_de_stage.ecran_accueil
import crosemont.dti.g55.application_gestion_de_stage.présentation.login.IPrésentateurLogin
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.IModèle
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.Modèle
import crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées.SourceDeDonnéesException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginPrésentateur(val vue: ecran_accueil, modèle: IModèle = Modèle.instance): IPrésentateurLogin {

    private val _modèle =modèle
    override fun traiter_login() {
        val courriel = vue.obtenir_courriel()
        val mot_de_passe = vue.obtenir_mot_de_passe()

        if (courriel == "") {
            vue.afficher_erreur("Le courriel ne peut être vide")
            return
        }
        if (mot_de_passe == "") {
            vue.afficher_erreur("Le mot de passe ne peut être vide")
            return
        }
        CoroutineScope( Dispatchers.IO ).launch {
            try{
                _modèle.login( courriel, mot_de_passe )
                CoroutineScope( Dispatchers.Main ).launch {
                    vue.naviguer_vers_accueil()
                }
            }
            catch(e : SourceDeDonnéesException){
                CoroutineScope( Dispatchers.Main ).launch {
                    vue.afficher_erreur( e.message ?: "Erreur Inconnue" )
                    val erreurMessage = when (e) {
                        is SourceDeDonnéesException -> {
                            val requeteCode = e.message?.substringAfter(":", "")?.substringBefore(" ")?.trim()
                            when (requeteCode) {
                                "401" -> " Vous n'êtes pas autorisé."
                                "403" -> " Nom d'utilisateur ou mot de passe incorrect."
                                else -> {
                                    val causeMessage = "Erreur inconnue"
                                    "$causeMessage"
                                }
                            }
                        }
                        else -> "Erreur inconnue"
                    }
                    vue.afficher_erreur(erreurMessage)

                }
            }

        }
    }
}