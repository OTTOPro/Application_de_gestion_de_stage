package crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées

import android.util.Base64
import android.util.JsonReader
import android.util.JsonToken
import com.google.gson.JsonParser
import crosemont.dti.g55.application_gestion_de_stage.domaine.Candidature
import crosemont.dti.g55.application_gestion_de_stage.domaine.Employeur
import crosemont.dti.g55.application_gestion_de_stage.domaine.Entreprise
import crosemont.dti.g55.application_gestion_de_stage.domaine.Etudiant
import crosemont.dti.g55.application_gestion_de_stage.domaine.ModeEmploi
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre
import crosemont.dti.g55.application_gestion_de_stage.domaine.ProfilInformatique
import crosemont.dti.g55.application_gestion_de_stage.domaine.Stage
import crosemont.dti.g55.application_gestion_de_stage.domaine.StageProgression
import crosemont.dti.g55.application_gestion_de_stage.domaine.ÉtatCandiature
import java.io.StringReader
import java.nio.charset.Charset
import kotlin.properties.Delegates

class DecodeurJSON {

    companion object {

        fun décoderJsonVersIdToken(json: String): String {
            var idToken: String? = null
            println("Voici le token complet :" +json)
            val reader = JsonReader(StringReader(json))
            reader.beginObject()
            while (reader.hasNext()) {
                val clé = reader.nextName()
                if (clé == "id_token") {
                    idToken = reader.nextString()
                } else {
                    reader.skipValue()
                }
            }
            reader.endObject()

            if (idToken == null) {
                throw SourceDeDonnéesException("Pas de id_token reçu")
            }

            return idToken
        }
        fun décoderJsonVersAvatarUrl(token: String): String {
            try {
                val parts = token.split("\\.".toRegex()).toTypedArray()
                if (parts.size > 1) {
                    val payload = parts[1]
                    val decodedPayload = String(Base64.decode(payload, Base64.URL_SAFE), Charset.forName("UTF-8"))

                    val json = JsonParser().parse(decodedPayload).asJsonObject
                    if (json.has("picture")) {
                        return json.get("picture").asString
                    } else {
                        throw SourceDeDonnéesException("Clé 'picture' non trouvée dans la charge utile du token.")
                    }
                } else {
                    throw SourceDeDonnéesException("Le token ne contient pas deux parties.")
                }
            } catch (e: Exception) {
                throw SourceDeDonnéesException("Erreur lors de l'extraction de l'URL de l'avatar depuis le token : ${e.message}")
            }
        }

        fun décoderJsonVersToken(json: String): String {
            println("Voici le token complet :" +json)
            var token : String? = null
            val reader = JsonReader(StringReader(json))
            reader.beginObject()
            while (reader.hasNext()) {
                val clé = reader.nextName()
                if (clé == "access_token") {
                    token = reader.nextString()
                } else {
                    reader.skipValue()
                }
            }
            reader.endObject()

            if( token == null) {
                throw SourceDeDonnéesException( "Pas de token reçues" )
            }

            return token
        }

        fun décoderJsonVersListeCandidatures(json: String): List<Candidature> {
            val candidatures = mutableListOf<Candidature>()

            JsonReader(StringReader(json)).use { reader ->
                reader.beginArray()
                while (reader.hasNext()) {
                    candidatures.add(décoderJsonVersCandidature(reader))
                }
                reader.endArray()
            }

            return candidatures
        }

        private fun décoderJsonVersCandidature(reader: JsonReader): Candidature {
            var codeCandidature = 0
            lateinit var offre: Offre
            lateinit var etudiant: Etudiant
            lateinit var étatCandidature: ÉtatCandiature

            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "codeCandidature" -> codeCandidature = reader.nextInt()
                    "offre" -> offre = décoderJsonVersOffre(reader)
                    "étudiant" -> etudiant = décoderJsonVersEtudiant(reader)
                    "étatCandidature" -> étatCandidature = ÉtatCandiature.valueOf(reader.nextString())
                    else -> reader.skipValue()
                }
            }
            reader.endObject()

            return Candidature(codeCandidature, offre, etudiant, étatCandidature)
        }


        fun décoderJsonVersListeEtudiants(json: String): List<Etudiant> {
            val étudiants = mutableListOf<Etudiant>()

            JsonReader(StringReader(json)).use { reader ->
                if (reader.peek() == JsonToken.BEGIN_ARRAY) {
                    reader.beginArray()
                    while (reader.hasNext()) {
                        étudiants.add(décoderJsonVersEtudiant(reader))
                    }
                    reader.endArray()
                } else if (reader.peek() == JsonToken.BEGIN_OBJECT) {
                    étudiants.add(décoderJsonVersEtudiant(reader))
                }
            }

            return étudiants
        }

        private fun décoderJsonVersEtudiant(reader: JsonReader): Etudiant {
            lateinit var codeUtilisateur : String
            lateinit var nomUtilisateur: String
            lateinit var prénomUtilisateur: String
            lateinit var courrielUtilisateur: String
            lateinit var téléphoneUtilisateur: String
            lateinit var profilInformatique: ProfilInformatique
            var stageIntégration = false
            var adresseEtudiant = ""

            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "codeUtilisateur" -> codeUtilisateur = reader.nextString()
                    "nomUtilisateur" -> nomUtilisateur = reader.nextString()
                    "prénomUtilisateur" -> prénomUtilisateur = reader.nextString()
                    "courrielUtilisateur" -> courrielUtilisateur = reader.nextString()
                    "téléphoneUtilisateur" -> téléphoneUtilisateur = reader.nextString()
                    "profilInformatique" -> profilInformatique = ProfilInformatique.valueOf(reader.nextString())
                    "stageIntégration" -> stageIntégration = reader.nextBoolean()
                    "adresseÉtudiant" -> adresseEtudiant = reader.nextString()
                    else -> reader.skipValue()
                }
            }
            reader.endObject()

            return Etudiant(
                codeUtilisateur,
                nomUtilisateur,
                prénomUtilisateur,
                courrielUtilisateur,
                téléphoneUtilisateur,
                profilInformatique,
                stageIntégration,
                adresseEtudiant
            )
        }

        private fun décoderJsonVersStage(reader: JsonReader): Stage {
            var idStage = 0
            lateinit var stagiaire: Etudiant
            lateinit var candidatureAcceptée: Candidature
            lateinit var superviseurAssigné: String
            lateinit var employeur: Employeur
            lateinit var stageProgression: StageProgression
            lateinit var lieu: String
            lateinit var dateDébut: String
            lateinit var dateFin: String

            reader.beginObject()
            while (reader.hasNext()) {
                when (val clé = reader.nextName()) {
                    "idStage" -> idStage = reader.nextInt()
                    "stagiaire" -> stagiaire = décoderJsonVersEtudiant(reader)
                    "candidatureAcceptée" -> candidatureAcceptée = décoderJsonVersCandidature(reader)
                    "superviseurAssigné" -> superviseurAssigné = reader.nextString()
                    "employeur" -> employeur = décoderJsonVersEmployeur(reader)
                    "stageProgression" -> stageProgression =   StageProgression.valueOf(reader.nextString())
                    "lieu" -> lieu = reader.nextString()
                    "dateDébut" -> dateDébut = reader.nextString()
                    "dateFin" -> dateFin = reader.nextString()

                    else -> reader.skipValue()
                }
            }
            reader.endObject()

            return Stage(idStage, stagiaire, candidatureAcceptée, superviseurAssigné, employeur, stageProgression, lieu, dateDébut, dateFin)
        }

        fun décoderJsonVersListeStages(json: String): List<Stage> {
            val stages = mutableListOf<Stage>()

            JsonReader(StringReader(json)).use { reader ->
                reader.beginArray()
                while (reader.hasNext()) {
                    stages.add(décoderJsonVersStage(reader))
                }
                reader.endArray()
            }

            return stages
        }

        fun décoderJsonVersListeOffres(json: String): List<Offre> {
            val offres = mutableListOf<Offre>()

            JsonReader(StringReader(json)).use { reader ->
                reader.beginArray()
                while (reader.hasNext()) {
                    offres.add(décoderJsonVersOffre(reader))
                }
                reader.endArray()
            }

            return offres
        }

        private fun décoderJsonVersOffre(reader: JsonReader): Offre {
            var idOffre = 0
            lateinit var employeur: Employeur
            lateinit var titrePoste: String
            lateinit var modeEmploi: ModeEmploi
            lateinit var description: String
            var candidatures: List<Candidature> = mutableListOf()

            reader.beginObject()
            while (reader.hasNext()) {
                when (val clé = reader.nextName()) {
                    "idOffre" -> idOffre = reader.nextInt()
                    "employeur" -> employeur = décoderJsonVersEmployeur(reader)
                    "titrePoste" -> titrePoste = reader.nextString()
                    "modeEmploi" -> modeEmploi = ModeEmploi.valueOf(reader.nextString())
                    "description" -> description = reader.nextString()
                    "candidatures" -> {
                        candidatures = décoderJsonVersListeCandidaturesParOffres(reader)
                    }
                    else -> reader.skipValue()
                }
            }
            reader.endObject()

            return Offre(idOffre, employeur, titrePoste, modeEmploi, description, false, candidatures)
        }

        private fun décoderJsonVersListeCandidaturesParOffres(reader: JsonReader): List<Candidature> {
            val candidatures = mutableListOf<Candidature>()

            reader.beginArray()
            while (reader.hasNext()) {
                candidatures.add(décoderJsonVersCandidature(reader))
            }
            reader.endArray()

            return candidatures
        }

        private fun décoderJsonVersEmployeur(reader: JsonReader): Employeur {
            lateinit var codeUtilisateur : String
            lateinit var nomUtilisateur: String
            lateinit var prénomUtilisateur: String
            lateinit var courrielUtilisateur: String
            lateinit var téléphoneUtilisateur: String
            lateinit var codeEntreprise: Entreprise

            reader.beginObject()
            while (reader.hasNext()) {
                when (val clé = reader.nextName()) {
                    "codeUtilisateur" -> codeUtilisateur = reader.nextString()
                    "nomUtilisateur" -> nomUtilisateur = reader.nextString()
                    "prénomUtilisateur" -> prénomUtilisateur = reader.nextString()
                    "courrielUtilisateur" -> courrielUtilisateur = reader.nextString()
                    "téléphoneUtilisateur" -> téléphoneUtilisateur = reader.nextString()
                    "codeEntreprise" -> codeEntreprise = décoderJsonVersEntreprise(reader)
                    else -> reader.skipValue()
                }
            }
            reader.endObject()

            return Employeur(codeUtilisateur, nomUtilisateur, prénomUtilisateur, courrielUtilisateur, téléphoneUtilisateur, codeEntreprise)
        }

        private fun décoderJsonVersEntreprise(reader: JsonReader): Entreprise {
            var codeEntreprise = 0
            lateinit var nomEntreprise: String
            lateinit var adresseEntreprise: String

            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "codeEntreprise" -> codeEntreprise = reader.nextInt()
                    "nomEntreprise" -> nomEntreprise = reader.nextString()
                    "adresseEntreprise" -> adresseEntreprise = reader.nextString()
                    else -> reader.skipValue()
                }
            }
            reader.endObject()

            return Entreprise(codeEntreprise, nomEntreprise, adresseEntreprise)
        }
    }
}
