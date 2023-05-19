package com.example.taskservice.domain.repository;

public final class Queries {
    public static final String findMostCriticalTasksByUserIdQuery = """
            SELECT * FROM public.task\s
            WHERE user_id=:userId\s
            AND status IN (0,1)\s
            AND deadline =\s
            (SELECT MIN(deadline) FROM public.task\s
             WHERE user_id=:userId AND status IN (0,1))\s
             AND priority = (SELECT MAX(priority) FROM\s
            \t\t\t\t(SELECT * FROM public.task\s
            \t\t\t\t WHERE user_id=:userId\s
            \t\t\t\t AND status IN (0,1)\s
            \t\t\t\t AND deadline =\s
            \t\t\t\t (SELECT MIN(deadline)\s
            \t\t\t\t  FROM public.task\s
            \t\t\t\t  WHERE user_id=:userId\s
            \t\t\t\t  AND status IN (0,1))) as subquery)""";
    public static final String getAllTasksQuery = "SELECT * FROM public.task WHERE user_id=:userId";
    public static final String getOverdueTasksQuery = "SELECT * FROM public.task " +
            "WHERE (deadline < :currentDate AND user_id = :userId)";
}
