package crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs

import crosemont.dti.g55.application_gestion_de_stage.ecran_profil_etudiant
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.IModèle
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.Modèle
import crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées.SourceDeDonnéesException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EtudiantPrésentateur(val vue: ecran_profil_etudiant,modèle: IModèle = Modèle.instance) {

    private val _modèle =modèle

    fun chargerEtudiant(){
        CoroutineScope( Dispatchers.IO ).launch {
            try {
                withContext(Dispatchers.Main) {
                    vue.afficherEcranChargement(true)
                }
                delay(1000)
                val etudiant = _modèle.obtenirEtudiant()

                CoroutineScope( Dispatchers.Main ).launch {
                    vue.afficherEtudiant(etudiant)
                    vue.afficherEcranChargement(false)
                }
            } catch (e: SourceDeDonnéesException) {
                withContext(Dispatchers.Main) {
                    vue.afficherErreur(e.message ?: "Erreur inconnue")
                    vue.afficherEcranChargement(false)
                }
            }
        }
    }

    fun chargerAvater(){
        CoroutineScope( Dispatchers.IO ).launch {
            val avatar = _modèle.obtenirAvatar()
            CoroutineScope( Dispatchers.Main ).launch {
                vue.afficherAvatar(avatar)
            }
        }
    }
}