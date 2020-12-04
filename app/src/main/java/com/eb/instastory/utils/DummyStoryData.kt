package com.eb.instastory.utils

import com.eb.instastory.pojo.Story
import com.eb.instastory.pojo.User

/**
 * Created by ebayhan on 12/3/20.
 */
object DummyStoryData {

    fun getStoryUsers(): ArrayList<User> {
        return arrayListOf(
                User("pambeesly", "https://randomuser.me/api/portraits/women/3.jpg",
                        arrayListOf(
                                Story(1, "https://i.picsum.photos/id/401/1080/1920.jpg?hmac=BK5RkmQf_BHk8_YVBXMncuP9s0_4-DlbVfEfTOoab-E", System.currentTimeMillis() - 11 * HOUR),
                                Story(2, "https://player.vimeo.com/external/403295268.sd.mp4?s=3446f787cefa52e7824d6ce6501db5261074d479&profile_id=165&oauth2_token_id=57447761", System.currentTimeMillis() - 6 * HOUR),
                                Story(3, "https://i.picsum.photos/id/33/1080/1920.jpg?hmac=Gh8zw2IC18vp6QNTy4QxLJgvS4IGyhVoYylWI3H9oEk", System.currentTimeMillis() - 56 * MIN),
                                Story(4, "https://i.picsum.photos/id/217/1080/1920.jpg?hmac=Mgf4vR5p4C2Gagy4TG71klpnDzhAKPuDRLOqawexQSg", System.currentTimeMillis() - 3 * MIN)
                        )
                ),
                User("michaelscott", "https://randomuser.me/api/portraits/men/78.jpg",
                        arrayListOf(
                                Story(1, "https://i.picsum.photos/id/992/1080/1920.jpg?hmac=Ze1e6OdkbsCf77DVhTpwv3YcW1nQKdr-OTgaU-Tn4zw", System.currentTimeMillis() - 5 * HOUR),
                                Story(2, "https://i.picsum.photos/id/439/1080/1920.jpg?hmac=x0zWOi-X-2a2fDGpbwAIfxmErMLL6TsBaHp_XXdlI3w", System.currentTimeMillis() - 5 * HOUR),
                                Story(3, "https://player.vimeo.com/external/403295710.sd.mp4?s=788b046826f92983ada6e5caf067113fdb49e209&profile_id=165&oauth2_token_id=57447761", System.currentTimeMillis() - 5 * HOUR),
                                Story(4, "https://i.picsum.photos/id/695/1080/1920.jpg?hmac=igeGgWfw6gO7aW5pNY7082kGFjtp_7U-mnRIFB_bekw", System.currentTimeMillis() - 17 * MIN)
                        )
                ),
                User("jimhalpert", "https://randomuser.me/api/portraits/men/3.jpg",
                        arrayListOf(
                                Story(1, "https://i.picsum.photos/id/477/1080/1920.jpg?hmac=3Py3pqkD1n_QgEvXhtUEWaQLpdRB_Me7KDjFk7salDE", System.currentTimeMillis() - 8 * HOUR),
                                Story(2, "https://i.picsum.photos/id/193/1080/1920.jpg?hmac=36Guotz7UKHZvFJ5Po8pdQ0lseb167-mVgqbncLnqFQ", System.currentTimeMillis() - 6 * HOUR),
                                Story(3, "https://i.picsum.photos/id/535/1080/1920.jpg?hmac=n4Vy0A1d7vfukBb_u-IOkrhMjj_ONMm7ejoH4fGtJ_4", System.currentTimeMillis() - 4 * HOUR),
                                Story(4, "https://i.picsum.photos/id/986/1080/1920.jpg?hmac=abLoRGL3AaAVb1_sGbtk_W2SDdK0f1LYKYlW1-i45o8", System.currentTimeMillis() - 2 * HOUR),
                                Story(5, "https://i.picsum.photos/id/530/1080/1920.jpg?hmac=GRvJTKv6nFbB56tRFtwRyZQAOuF0_hVR3xhBrSQ5vBA", System.currentTimeMillis() - 1 * HOUR)
                        )
                ),
                User("erinhannon", "https://randomuser.me/api/portraits/women/63.jpg",
                        arrayListOf(
                                Story(1, "https://player.vimeo.com/external/394678700.sd.mp4?s=353646e34d7bde02ad638c7308a198786e0dff8f&profile_id=165&oauth2_token_id=57447761", System.currentTimeMillis() - 5 * HOUR),
                                Story(2, "https://i.picsum.photos/id/905/1080/1920.jpg?hmac=Qs642TX51BVCBJnHj9FFxl5XTT4-Knl7pPpBKzNT930", System.currentTimeMillis() - 5 * HOUR),
                                Story(3, "https://i.picsum.photos/id/975/1080/1920.jpg?hmac=Hdq341G8N0KPBaPW8sneGj_W886OZ9_Pu5D-Bu8T9cM", System.currentTimeMillis() - 5 * HOUR),
                        )
                )
        )
    }
}