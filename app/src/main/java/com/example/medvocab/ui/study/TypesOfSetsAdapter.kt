package com.example.medvocab.ui.study

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.medvocab.MainViewModel
import com.example.medvocab.TypesList
import com.example.medvocab.databinding.CardWordTypesBinding
import com.google.firebase.auth.FirebaseAuth


class TypesOfSetsAdapter( private val viewModel: MainViewModel, private val context : Context) :
    ListAdapter<TypesList.TypesOfSets, TypesOfSetsAdapter.VH>(RedditDiff()) {


    inner class VH( cardWordTypesBinding: CardWordTypesBinding)
        : RecyclerView.ViewHolder(cardWordTypesBinding.root)
    {
        val type = cardWordTypesBinding.type
        //val masteredWordsCount = cardWordTypesBinding.masteredWordsCount
        val button = cardWordTypesBinding.practiceThisBt

        init {
            button.setOnClickListener {
                //TODO : set adapter position var in viewmodel live data
                Log.d("Item clicked : ", adapterPosition.toString())
                if(FirebaseAuth.getInstance().currentUser != null)
                    viewModel.newTypeSelected(adapterPosition)
                else
                {
                    Toast.makeText(context, "Please sign in to continue !!!", Toast.LENGTH_SHORT).show()
                }

            }
        }

        fun bind(typeVar : TypesList.TypesOfSets)
        {
            type.text = typeVar.type
            //Log.d("mastered words : ", "${typeVar.masteredWords}")
            //masteredWordsCount.text = "${typeVar.masteredWords} of 21 words mastered"
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = CardWordTypesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        // return super.getItemCount()

        return currentList.size
    }


    class RedditDiff : DiffUtil.ItemCallback<TypesList.TypesOfSets>()
    {
        override fun areItemsTheSame(oldItem: TypesList.TypesOfSets, newItem: TypesList.TypesOfSets): Boolean {

            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: TypesList.TypesOfSets, newItem: TypesList.TypesOfSets): Boolean {

            return (oldItem.type == newItem.type) &&
                    (oldItem.masteredWords == newItem.masteredWords)

        }
    }

}
