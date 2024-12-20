package com.sfjs.gql.resolvers;

import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
@Transactional
public class JobListing {

    @MutationMapping(name = "saveJobListing")
    public void saveJobListing() throws Exception {
        System.out.println("saveJobListing");
        return;
    }

}
