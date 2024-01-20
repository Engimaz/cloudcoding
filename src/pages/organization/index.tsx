import { useEffect, useState } from "react"
import OrganizationCard from './organization-card.tsx'
import { Organization, OrganizationVO } from "@/api/manager/types.ts"
import { Button } from "primereact/button"
import CreatePanel from "../setting/organization-manager/dialog-panel.tsx"
import { getOrganizationList } from "@/api/manager/index.ts"

export default function index() {
  const [data, setData] = useState<Array<OrganizationVO>>([])

  useEffect(() => {
    getOrganizationList(1, 10, "", "OrgPass").then(res => {
      if (res.code >= 200) {
        setData(res.result.list)
      }
    })
  }, [])

  const [editRecord, setEditRecord] = useState<Organization>({
    id: "-1", name: "",
    avatar: "",// 组织头像
    img: "",// 宣传图
    description: "",//组织介绍
    location: "",// 组织地址 省区级
    address: "",//详细地址
    type: "",//组织类型
    status: "OrgAudit",//组织状态
    positions: [],
    features: [],
    userPositions: []
  } as Organization)
  return (

    <main className="w-full h-screen flex flex-wrap justify-center items-center gap-2  overflow-hidden relative">
      <section className="w-full h-screen flex flex-wrap justify-center items-center gap-2 ">
        {
          data.map((item: OrganizationVO) => (<OrganizationCard key={item.id} data={item}></OrganizationCard>))
        }
      </section>

      <div className='absolute bottom-20 right-10'>
        <div className="flex flex-wrap justify-content-center gap-3 mb-4">
          <Button icon="iconfont icon-plus" rounded aria-label="Filter" onClick={() => setEditRecord({
            id: "new-organization",
            name: "",
            avatar: "",// 组织头像
            img: "",// 宣传图
            description: "",//组织介绍
            location: "",// 组织地址 省区级
            address: "",//详细地址
            type: "",//组织类型
            status: "OrgAudit",//组织状态
            positions: [],
            features: [],
            userPositions: []
          } as Organization)} />
        </div>
      </div>
      <CreatePanel editRecord={editRecord} onSussess={() => { setEditRecord({ id: "-1" } as Organization) }} />

    </main>
  )
}
