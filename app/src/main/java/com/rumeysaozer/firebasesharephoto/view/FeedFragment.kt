package com.rumeysaozer.firebasesharephoto.view

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rumeysaozer.firebasesharephoto.R
import com.rumeysaozer.firebasesharephoto.adapter.PostAdapter
import com.rumeysaozer.firebasesharephoto.databinding.FragmentFeedBinding
import com.rumeysaozer.firebasesharephoto.databinding.FragmentMainBinding
import com.rumeysaozer.firebasesharephoto.model.Post


class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    val postList : ArrayList<Post> = ArrayList()
    var adapter : PostAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        firestore = Firebase.firestore


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        getData()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = PostAdapter(postList)
        binding.recyclerView.adapter = adapter
    }
    private fun getData(){
        firestore.collection("Post").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error != null){
                Toast.makeText(requireContext(),error.localizedMessage, Toast.LENGTH_LONG).show()
            }else{
                if(value != null){
                    if(!value.isEmpty){
                     val documents =  value.documents
                        postList.clear()
                        for(document in documents){
                          val comment =  document.get("comment") as String
                          val email = document.get("email") as String
                          val downloadUrl = document.get("downloadUrl") as String
                          val post = Post(email, comment, downloadUrl)
                            println(comment)
                          postList.add(post)
                        }
                        adapter!!.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_item,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.addPost){
            val action = FeedFragmentDirections.actionFeedFragment2ToUploadFragment()
            findNavController().navigate(action)
        }
        if(item.itemId == R.id.signOut){
         auth.signOut()
            val action = FeedFragmentDirections.actionFeedFragment2ToMainFragment()
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

}