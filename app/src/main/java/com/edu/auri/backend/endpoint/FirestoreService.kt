package com.edu.auri.backend.endpoint

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreService(private val firestore: FirebaseFirestore) {

    suspend fun getCollection(
        collectionPath: String
    ): Result<List<Map<String, Any>>> {
        return try {
            val documents = firestore
                .collection(collectionPath)
                .get()
                .await()
            val data = documents.documents.map { it.data ?: emptyMap() }
            Result.success(data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDocument(
        documentPath: String
    ): Result<Map<String, Any>> {
        return try {
            val document = firestore
                .document(documentPath)
                .get()
                .await()
            Result.success(document.data ?: emptyMap())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}