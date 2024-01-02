import { generateMockCourses } from "@/api/course/mock.ts"
import { Course } from "@/api/course/types.ts"
import { useState } from "react"
import CourseCard from './course-card.tsx'

export default function index() {
    const [data, setData] = useState<Array<Course>>(generateMockCourses(10))
    return (

        <main className="flex flex-wrap justify-center items-center gap-2">
            {
                data.map((item: Course) => (<CourseCard key={item.id} data={item}></CourseCard>))

            }
        </main>

    )
}
