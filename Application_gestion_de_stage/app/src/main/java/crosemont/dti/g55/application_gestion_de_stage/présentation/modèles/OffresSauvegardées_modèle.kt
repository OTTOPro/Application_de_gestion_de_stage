// OffreModel.kt
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import crosemont.dti.g55.application_gestion_de_stage.domaine.Employeur
import crosemont.dti.g55.application_gestion_de_stage.domaine.Entreprise
import crosemont.dti.g55.application_gestion_de_stage.domaine.ModeEmploi
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre

class OffresSauvegardées_modèle(context: Context) {

    private val dbHelper = AideBD(context)

    fun sauvegarderOffre(offre: Offre) {
        val bd = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(AideBD.COLUMN_TITRE_DU_POSTE, offre.titrePoste)
            put(AideBD.COLUMN_EMPLOYEUR, offre.employeur.codeEntreprise.nomEntreprise)
            put(AideBD.COLUMN_TYPE, offre.modeEmploi.name)
            put(AideBD.COLUMN_LIEU, offre.employeur.codeEntreprise.adresseEntreprise)
            put(AideBD.COLUMN_DESCRIPTION, offre.description)
            put(AideBD.COLUMN_OFFRE_SAUVEGARDEE, 1) // true
        }

        val nouveauOffreSauvegardée = bd.insert(AideBD.TABLE_OFFRES, null, values)
        bd.close()

        if (nouveauOffreSauvegardée != -1L) {
            Log.d(TAG, "Offer saved successfully. Row ID: $nouveauOffreSauvegardée")
        } else {
            Log.e(TAG, "Error saving offer.")
        }
    }

    fun obtenirOffresSauvegardées(): List<Offre> {
        val offres = mutableListOf<Offre>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            AideBD.TABLE_OFFRES,
            null,
            "${AideBD.COLUMN_OFFRE_SAUVEGARDEE} = 1", // Only retrieve saved offers
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(AideBD.COLUMN_ID))
            val titreDuPoste = cursor.getString(cursor.getColumnIndex(AideBD.COLUMN_TITRE_DU_POSTE))
            val employeur = cursor.getString(cursor.getColumnIndex(AideBD.COLUMN_EMPLOYEUR))
            val type = cursor.getString(cursor.getColumnIndex(AideBD.COLUMN_TYPE))
            val lieu = cursor.getString(cursor.getColumnIndex(AideBD.COLUMN_LIEU))
            val description = cursor.getString(cursor.getColumnIndex(AideBD.COLUMN_DESCRIPTION))
            val offreSauvegardee = cursor.getInt(cursor.getColumnIndex(AideBD.COLUMN_OFFRE_SAUVEGARDEE)) == 1

            var i = 0
            val employeurObjet = Employeur(i.toString(), " ", " ", " ", " ",
                Entreprise(i, employeur, lieu))
            i++

            val modeEmploiInstance = ModeEmploi.valueOf(type)

            val offre = Offre(id, employeurObjet, titreDuPoste, modeEmploiInstance, description, offreSauvegardee)
            offres.add(offre)
        }

        cursor.close()
        db.close()

        return offres
    }

    companion object {
        private const val TAG = "OffreDatabaseModel"
    }
}
