public class UserFeedback {
    private String firstName; 
    private String lastName;       
    private String email;          
    private String completeFeedback;
    private String reviewID;        
    private boolean longFeedback;   

    public UserFeedback(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void analyseFeedback(boolean isConcatenation, String sent1, String sent2, String sent3, String sent4, String sent5) 
        if (isConcatenation) {
            this.completeFeedback = feedbackUsingConcatenation(sent1, sent2, sent3, sent4, sent5);
        } else {
            this.completeFeedback = feedbackUsingStringBuilder(sent1, sent2, sent3, sent4, sent5).toString();
        }
        
        this.longFeedback = checkFeedbackLength(this.completeFeedback);
        
        createReviewID(this.firstName, this.lastName, this.completeFeedback);
    }

    private String feedbackUsingConcatenation(String sent1, String sent2, String sent3, String sent4, String sent5) {
        String concatenatedFeedback = sent1 + sent2 + sent3 + sent4 + sent5;
        return concatenatedFeedback;
    }

    private StringBuilder feedbackUsingStringBuilder(String sent1, String sent2, String sent3, String sent4, String sent5) {
        StringBuilder sb = new StringBuilder();
        sb.append(sent1)
          .append(sent2)
          .append(sent3)
          .append(sent4)
          .append(sent5);
        return sb;
    }

    private boolean checkFeedbackLength(String completeFeedback) {
        boolean isLong = (completeFeedback != null && completeFeedback.length() > 500);
        this.longFeedback = isLong;
        return isLong;
    }

    private void createReviewID(String firstName, String lastName, String completeFeedback) {
        String fullName = firstName + lastName;
        String nameSub = fullName.substring(Math.min(2, fullName.length()), Math.min(6, fullName.length())).toUpperCase();
        
        String feedbackSub = "";
        if (completeFeedback != null && completeFeedback.length() >= 15) {
            feedbackSub = completeFeedback.substring(10, 15).toLowerCase();
        } else if (completeFeedback != null && completeFeedback.length() > 10) {
            feedbackSub = completeFeedback.substring(10).toLowerCase(); 
        }
        
        int feedbackLength = (completeFeedback != null) ? completeFeedback.length() : 0;
        
        long timestamp = System.currentTimeMillis();
    
        String reviewId = (nameSub + feedbackSub + feedbackLength + "_" + timestamp).replace(" ", "");
        
        this.reviewID = reviewId;
    }

    @Override
    public String toString() {
        return "UserFeedback{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", completeFeedback='" + completeFeedback + '\'' +
                ", longFeedback=" + longFeedback +
                ", reviewID='" + reviewID + '\'' +
                '}';
    }
    public static void main(String[] args) {
        String sent1 = "I was very satisfied with the service.";
        String sent2 = "The e-Bike is quite comfortable to ride.";
        String sent3 = "The battery life of the e-Bike is impressive.";
        String sent4 = "The customer support was helpful and responsive.";
        String sent5 = "I would recommend this e-Bike to my friends and family.";

        UserFeedback userFeedback = new UserFeedback("John", "Doe", "john.doe@example.com");

        userFeedback.analyseFeedback(false, sent1, sent2, sent3, sent4, sent5);
        
        System.out.println(userFeedback);
    }
}