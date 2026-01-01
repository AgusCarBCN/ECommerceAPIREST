package com.carnerero.agustin.ecommerceapplication.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserStatistics {
    private long totalUsers;
    private long activeUsers;
    private long inactiveUsers;
    private long newUsersToday;
}
