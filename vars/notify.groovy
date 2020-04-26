import com.mycompany.colinbut.Constants

def call(Map config=[:]) {
    if (config.type == "slack") {
        echo Constants.SLACK_MESSAGE_HEADER
    } else {
        echo Constants.EMAIL_MESSAGE_HEADER
    }
}