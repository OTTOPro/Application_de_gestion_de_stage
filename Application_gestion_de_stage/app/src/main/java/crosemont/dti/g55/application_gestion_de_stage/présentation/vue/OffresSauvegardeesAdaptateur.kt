package crosemont.dti.g55.application_gestion_de_stage.présentation.vue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import crosemont.dti.g55.application_gestion_de_stage.R
import androidx.recyclerview.widget.RecyclerView
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre

class OffresSauvegardeesAdaptateur(private var offresSauvegardées: List<Offre>) : RecyclerView.Adapter<OffresSauvegardeesAdaptateur.OffresSauvegardeesViewHolder>() {

    inner class OffresSauvegardeesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val offre_entreprise:TextView = itemView.findViewById(R.id.entreprise_offre)
        val offre_lieu:TextView = itemView.findViewById(R.id.lieu_offre)
        val offre_type:TextView = itemView.findViewById(R.id.type_offre)
        val offre_description:TextView = itemView.findViewById(R.id.description_offre)
        val offre_sauvegardée_image:ImageButton = itemView.findViewById(R.id.btn_favoris)
        val favorisImageButton: ImageButton = itemView.findViewById(R.id.btn_favoris)
        var offreSauvegardeÉtat = 0
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OffresSauvegardeesAdaptateur.OffresSauvegardeesViewHolder {
        val vueOffre = LayoutInflater.from(parent.context).inflate(R.layout.item_offres, parent, false)
        return OffresSauvegardeesViewHolder(vueOffre)
        //println("hellolooo" + sauvegarderOffrePrésentateur.chargerLesOffresSauvegardées())

    }

    override fun onBindViewHolder(
        holder: OffresSauvegardeesAdaptateur.OffresSauvegardeesViewHolder,
        position: Int
    ) {
        /*
        val offreSauvegardée = offresSauvegardées[position]

        holder.offre_entreprise.text = offreSauvegardée.titreDuPoste + " - " + offreSauvegardée.nomEntreprise
        holder.offre_lieu.text = offreSauvegardée.lieu
        holder.offre_type.text = offreSauvegardée.type
        holder.offre_description.text = offreSauvegardée.description

        if (offreSauvegardée.offreSauvegardée) {
            holder.favorisImageButton.setImageResource(R.drawable.favori_en_noir)
        }

        holder.favorisImageButton.setOnClickListener {
            holder.offreSauvegardeÉtat = if (holder.offreSauvegardeÉtat == 0) 1 else 0
            if (holder.offreSauvegardeÉtat == 0) {
                holder.favorisImageButton.setImageResource(R.drawable.favori)
                offreSauvegardée.offreSauvegardée = !offreSauvegardée.offreSauvegardée
                println("Offres S: " + "id: ${offreSauvegardée.id}, nomEntreprise: ${offreSauvegardée.nomEntreprise}, status: ${offreSauvegardée.offreSauvegardée}")
            } else {
                holder.favorisImageButton.setImageResource(R.drawable.favori_en_noir)
                offreSauvegardée.offreSauvegardée = !offreSauvegardée.offreSauvegardée
                //sauvegarderOffrePrésentateur.sauvegarderOffre(offre.id)
                holder.itemView.visibility = View.GONE
                println("Offres NS: " + "id: ${offreSauvegardée.id}, nomEntreprise: ${offreSauvegardée.nomEntreprise}, status: ${offreSauvegardée.offreSauvegardée}")
            }
        }

         */
    }

    override fun getItemCount(): Int {
        return offresSauvegardées.size
    }

}