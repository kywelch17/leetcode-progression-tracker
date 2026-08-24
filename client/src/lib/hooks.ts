import { useEffect, useState } from 'react';
import { apiClient } from './api';

import type { LeetcodeProblem, BackendProblem } from './types';

export const useProblemSync = () => {
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const loadAndCompare = async () => {
            setLoading(true);
            setError(null);

            try {
                const leetcodeProblems: LeetcodeProblem[] =
                    await apiClient.get<LeetcodeProblem[]>('/problems/leetcode');
                const backendProblems =
                    await apiClient.get<BackendProblem[]>('/problems');

                const backendProblemsUrlSet = new Set<string>(
                    backendProblems.map((problem) => problem.url)
                );
                const missingProblems = leetcodeProblems.filter(
                    (problem) => !backendProblemsUrlSet.has(problem.url)
                );

                // TODO: Figure out a way to do a batch instead of one call at a time
                await Promise.all(
                    missingProblems.map((problem) => {
                        apiClient.post('/problems', {
                            title: problem.title,
                            difficulty: problem.difficulty,
                            note: '',
                            url: problem.url,
                        });
                    })
                );
            } catch {
                setError('Failed to sync problems');
            } finally {
                setLoading(false);
            }
        };

        loadAndCompare();
    }, []);

    return { error, loading };
};
