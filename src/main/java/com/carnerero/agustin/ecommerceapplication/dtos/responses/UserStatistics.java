package com.carnerero.agustin.ecommerceapplication.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserStatistics {
    private long totalUsers;
    private long activeUsers;
    private long inactiveUsers;
    private long newUsersToday;
}
