package MeetingRoom_BookingSystem.RoomBooking.Controller

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClient

@RestController
@RequestMapping("/api/github")
class GithubApiController {

    private val restClient = RestClient.builder()
    .baseUrl("https://api.github.com")
    .build()


    @GetMapping("/repos")
    fun getUserRepositories(
        @RegisteredOAuth2AuthorizedClient("github") authorizedClient : OAuth2AuthorizedClient
    ): List<Map<String, Any>>? {
        val accessTokenValue = authorizedClient.accessToken.tokenValue

        return restClient.get()
            .uri ("/user/repos")
            .header("Authorization", "Bearer $accessTokenValue")
            .header("Accept", "application/vnd.github+json")
            .retrieve()
            .body(List::class.java) as? List<Map<String, Any>>
    }
}