package crosemont.dti.g55.application_gestion_de_stage.sourceDeDonnées

import android.util.JsonWriter
import crosemont.dti.g55.application_gestion_de_stage.domaine.Candidature
import crosemont.dti.g55.application_gestion_de_stage.domaine.Etudiant
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre
import crosemont.dti.g55.application_gestion_de_stage.domaine.Stage
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStreamWriter

class SourceDeDonneesHTTP( var url_api: String, var url_auth: String): SourceDeDonnees {

    override suspend fun obtenirDonnéesCandidatures(token: String): List<Candidature> {
        try {
            val client = OkHttpClient()
            val headers = Headers.Builder()
                .add("Authorization", "Bearer $token")
                .build()

            val requête = Request.Builder()
                .url("$url_api/etudiant/candidatures")
                .headers(headers)
                .build()

            val réponse = client.newCall(requête).execute()

            if (réponse.code != 200) {
                throw SourceDeDonnéesException("Erreur : ${réponse.code}")
            }
            if (réponse.body == null) {
                throw SourceDeDonnéesException("Pas de données reçues")
            }

            val jsonCandidatures = réponse.body!!.string()
            return DecodeurJSON.décoderJsonVersListeCandidatures(jsonCandidatures)
        } catch (e: IOException) {
            throw SourceDeDonnéesException(e.message ?: "Erreur inconnue")
        }
    }


    override suspend fun obtenirDonnéesOffres(): List<Offre> {
        try{
            val client = OkHttpClient()
            val requête = Request.Builder().url("$url_api/offres").build()

            val réponse = client.newCall( requête ).execute();

            if( réponse.code != 200 ){
                throw SourceDeDonnéesException( "Erreur :" + réponse.code )
            }
            if( réponse.body == null ){
                throw SourceDeDonnéesException( "Pas de données reçues" )
            }

            return DecodeurJSON.décoderJsonVersListeOffres( réponse.body!!.string() )
        }
        catch(e: IOException){
            throw SourceDeDonnéesException("Erreur de chargement, Ressayez plus tard!!")
        }
    }

    override suspend fun obtenirDonnéesEtudiant(token: String): List<Etudiant> {
        try {
            val client = OkHttpClient()
            val headers = Headers.Builder()
                .add("Authorization", "Bearer $token")
                .build()
            val requête = Request.Builder().headers(headers).url("$url_api/étudiant").build()

            val réponse = client.newCall(requête).execute()

            if (réponse.code != 200) {
                throw SourceDeDonnéesException("Erreur : ${réponse.code}")
            }
            if (réponse.body == null) {
                throw SourceDeDonnéesException("Pas de données reçues")
            }

            val jsonEtudiants = réponse.body!!.string()
            return DecodeurJSON.décoderJsonVersListeEtudiants(jsonEtudiants)
        } catch (e: IOException) {
            throw SourceDeDonnéesException("Erreur de chargement, Ressayez plus tard!!")
        }
    }

     override suspend fun AjouterUneCandidature(token: String,code_offre: Int): Boolean {
        try {
            val client = OkHttpClient()

            val jsonRequestBody = """
            {
                
            }
        """.trimIndent()

            val requestBody = jsonRequestBody.toRequestBody("application/json".toMediaTypeOrNull())
            val url = "$url_api/offres/$code_offre/candidatures"
            val headers = Headers.Builder()
                .add("Authorization", "Bearer $token")
                .build()
            val request = Request.Builder()
                .url(url)
                .headers(headers)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()

            if (response.code != 201) {
                throw SourceDeDonnéesException("Erreur : ${response.code}")
            }

            return true
        } catch (e: IOException) {
            throw SourceDeDonnéesException(e.message ?: "Erreur inconnue")
        }
    }

    override suspend fun supprimerCandidature(token: String, codeCandidature: Int): Boolean {
        try {
            val client = OkHttpClient()

            val url = "$url_api/candidatures/$codeCandidature"

            val headers = Headers.Builder()
                .add("Authorization", "Bearer $token")
                .build()

            val request = Request.Builder()
                .url(url)
                .headers(headers)
                .delete()
                .build()

            val response = client.newCall(request).execute()

            if (response.code != 204) {
                throw SourceDeDonnéesException("Erreur : ${response.code}")
            }else if(response.code == 409) {
                throw SourceDeDonnéesException("La candidature est deja accepte : ${response.code}")
            }

            return true
        } catch (e: IOException) {
            throw SourceDeDonnéesException(e.message ?: "Erreur inconnue")
        }
    }



    @Throws(SourceDeDonnéesException::class)
    override suspend fun obtenirUrl( lien: String) : String {
        try{
            val client = OkHttpClient()

            val output = ByteArrayOutputStream()
            val writer = JsonWriter( OutputStreamWriter( output ) )

            writer.beginObject()
            writer.name("lien").value( lien )
            writer.endObject()
            writer.close()

            val body = RequestBody.create(
                "application/json".toMediaTypeOrNull(), output.toString()
            )

            val requête = Request.Builder()
                .url( url_api )
                .post( body )
                .build()

            val réponse = client.newCall( requête ).execute()
            if(réponse.code == 200 ) {
                return réponse.body!!.string()
            }
            else {
                throw SourceDeDonnéesException("Code :" + réponse.code)
            }
        }
        catch(e: IOException){
            throw SourceDeDonnéesException(e.message ?: "Erreur inconnue")
        }
    }

    override suspend fun effectuerLogin(courriel: String, mot_de_passe: String) : String {
        try{
            val client = OkHttpClient()

            val output = ByteArrayOutputStream()
            val writer = JsonWriter( OutputStreamWriter( output ) )

            writer.beginObject()
            writer.name("username").value( courriel )
            writer.name("password").value( mot_de_passe )
            writer.name("grant_type").value( "password" )
            writer.name("audience").value("http://crosemont.dti.g66.service_gestion_stage")
            writer.endObject()
            writer.close()

            val body = RequestBody.create(
                "application/json".toMediaTypeOrNull(), output.toString()
            )

            val headers = Headers.Builder()
                .add( "Authorization", "Basic VDNRZTZOcFBEQk5BdWJ5YkllcEJwUTVGY0owanFnNmo6SXRfRzFCeXBSNzBvMElDLUJNT3ZVMFVPTy1tbGtyYWlxTmI5emFmSHlwVHczNnpISGtZekhoWkNUTmtyZE0zMg==" )
                .build()

            val requête = Request.Builder()
                .url( url_auth )
                .headers( headers )
                .post( body )
                .build()

            val réponse = client.newCall( requête ).execute();

            if( réponse.code != 200 ){
                throw SourceDeDonnéesException( "Erreur :" + réponse.code )
            }
            if( réponse.body == null ){
                throw SourceDeDonnéesException( "Pas de données reçues" )
            }

            return DecodeurJSON.décoderJsonVersToken( réponse.body!!.string() )
        }
        catch(e: IOException){
            throw SourceDeDonnéesException(e.message ?: "Erreur inconnue")
        }
    }

    override suspend fun effectuerLoginAvatar(courriel: String, mot_de_passe: String) : String {
        try{
            val client = OkHttpClient()

            val output = ByteArrayOutputStream()
            val writer = JsonWriter( OutputStreamWriter( output ) )

            writer.beginObject()
            writer.name("username").value( courriel )
            writer.name("password").value( mot_de_passe )
            writer.name("grant_type").value( "password" )
            writer.endObject()
            writer.close()

            val body = RequestBody.create(
                "application/json".toMediaTypeOrNull(), output.toString()
            )

            val headers = Headers.Builder()
                .add( "Authorization", "Basic VDNRZTZOcFBEQk5BdWJ5YkllcEJwUTVGY0owanFnNmo6SXRfRzFCeXBSNzBvMElDLUJNT3ZVMFVPTy1tbGtyYWlxTmI5emFmSHlwVHczNnpISGtZekhoWkNUTmtyZE0zMg==" )
                .build()

            val requête = Request.Builder()
                .url( url_auth )
                .headers( headers )
                .post( body )
                .build()

            val réponse = client.newCall( requête ).execute();

            if( réponse.code != 200 ){
                throw SourceDeDonnéesException( "Erreur :" + réponse.code )
            }
            if( réponse.body == null ){
                throw SourceDeDonnéesException( "Pas de données reçues" )
            }

            return DecodeurJSON.décoderJsonVersIdToken( réponse.body!!.string() )
        }
        catch(e: IOException){
            throw SourceDeDonnéesException(e.message ?: "Erreur inconnue")
        }
    }

    override suspend fun obtenirStage(token: String): List<Stage> {

        try{
            val client = OkHttpClient()

            val headers = Headers.Builder()
                .add("Authorization", "Bearer $token")
                .build()

            val requête = Request.Builder().headers(headers).url("$url_api/etudiants/stage").build()

            val réponse = client.newCall( requête ).execute();

            if( réponse.code != 200 ){
                throw SourceDeDonnéesException( "Erreur :" + réponse.code )
            }
            if( réponse.body == null ){
                throw SourceDeDonnéesException( "Pas de données reçues" )
            }

            return DecodeurJSON.décoderJsonVersListeStages( réponse.body!!.string() )
        }
        catch(e: IOException){
            throw SourceDeDonnéesException(e.message ?: "Erreur inconnue")
        }
    }

}