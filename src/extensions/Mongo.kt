package extensions

import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.withContext
import org.reactivestreams.Publisher

suspend fun<T> dbQuery(block: suspend  () -> T): T = withContext(Dispatchers.IO) {
    block()
}

suspend fun Publisher<UpdateResult>.awaitUpdateAcknowledgement(): Boolean = awaitFirstOrNull()?.wasAcknowledged() ?: false

suspend fun Publisher<InsertOneResult>.awaitInsertAcknowledgement(): Boolean = awaitFirstOrNull()?.wasAcknowledged() ?: false

suspend fun Publisher<DeleteResult>.awaitDeleteAcknowledgement(): Boolean = awaitFirstOrNull()?.wasAcknowledged() ?: false