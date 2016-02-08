package org.salesforce

import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.{BasicResponseHandler, DefaultHttpClient}

import com.google.gson.Gson
import com.typesafe.config._;

case class Token(access_token: String, instance_url: String, 
	id: String,
	token_type: String,
	issued_at: String,
	signature: String)

class Util {
  
	def getHost() : String = {
    val conf = ConfigFactory.load()
    val host = conf.getString("force.Host")
    return host
  }

  def getBaseUrl() : String = {
    val conf = ConfigFactory.load()
    val baseUrl = conf.getString("force.BaseUrl")
    return baseUrl
  }

  def getWaveBaseUrl() : String = {
    val conf = ConfigFactory.load()
    val waveBaseUrl = conf.getString("force.WaveBaseUrl")
    return waveBaseUrl
  }
	def getAccessToken() : String = {
	    	val login = "https://login.salesforce.com/services/oauth2/token"
	    	var access_token = ""
	    	try {
	    		val conf = ConfigFactory.load()
          val UserName = conf.getString("force.UserName")
          val PassWord     = conf.getString("force.PassWord")
			    val LoginURL     = conf.getString("force.LoginURL")
			    val GrantService = conf.getString("force.GrantService")
			    val ClientID     = conf.getString("force.ClientID")
			    val ClientSecret = conf.getString("force.ClientSecret")

                val loginURL = LoginURL +
                          GrantService +
                          "&client_id=" + ClientID +
                          "&client_secret=" + ClientSecret +
                          "&username=" + UserName +
                          "&password=" + PassWord

                val client = new DefaultHttpClient
                val post = new HttpPost(loginURL)
                val handler = new BasicResponseHandler();
                val response = client.execute(post)
                println("response:" +  response)
                val body = handler.handleResponse(response);
                println(response)
                val gson = new Gson
		        val jsonObject = gson.fromJson(body, classOf[Token])
		        access_token = jsonObject.access_token
		        println("access_token: " + access_token)
		        
                
			} catch {
			  case ioe: java.io.IOException =>  
			  case ste: java.net.SocketTimeoutException => 
			}
			return access_token

	    }
}