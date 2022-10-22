/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentPickupBinding
import com.example.cupcake.model.OrderViewModel

/**
 * [PickupFragment] allows the user to choose a pickup date for the cupcake order.
 */
class PickupFragment : Fragment() {

    // Binding object instance corresponding to the fragment_pickup.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment.
    private var binding: FragmentPickupBinding? = null

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentPickupBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = sharedViewModel
            pickupFragment = this@PickupFragment
        }

        fun gantiSementara() {
            val gambar: ImageView = binding!!.FotoHasbiw
            gambar.setImageResource(R.drawable.sudahbeneran2)
        }

        fun keNormal() {
            val gambar: ImageView = binding!!.FotoHasbiw
            gambar.setImageResource(R.drawable.sudahbeneran0)
        }

        val handler = Handler()
        val gambar: ImageView = binding!!.FotoHasbiw
        val tulisam: TextView = binding!!.VoiceNote
        val harusSekian = (4..7).random()
        val ucapan: List<String> = listOf("Hope all your birthday wishes come true!","Happy birthday","It’s your special day — get out there and celebrate!","Wishing you the biggest slice of happy today","I hope your celebration gives you many happy memories!","Cheers to you for another trip around the sun!")
        val ucapannya = ucapan.random()
        var sudahSekian = 0
        gambar.hasOnClickListeners()
        gambar.setOnClickListener {
            if (harusSekian > sudahSekian) {
                handler.postDelayed({
                    gantiSementara()
                }, 100)
                handler.postDelayed({
                    keNormal()
                }, 250)
                handler.postDelayed({
                    sudahSekian++
                }, 300)
                gambar.setImageResource(R.drawable.sudahbeneran1)
            } else {
                tulisam.text = ucapannya
                gambar.setImageResource(R.drawable.sudahbeneran3)
            }
        }
    }

    fun goToNextScreen() {
        findNavController().navigate(R.id.action_pickupFragment_to_startFragment)
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}