export type Difficulty = 'EASY' | 'MEDIUM' | 'HARD';

export interface LeetcodeProblem {
    id: number;
    title: string;
    titleSlug: string;
    difficulty: Difficulty;
    url: string;
}

export interface BackendProblem {
    id: number;
    title: string;
    difficulty: Difficulty;
    note: string;
    url: string;
    createdAt: string;
}
